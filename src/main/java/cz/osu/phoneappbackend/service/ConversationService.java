package cz.osu.phoneappbackend.service;

import cz.osu.phoneappbackend.dto.conversation.ConversationWindow;
import cz.osu.phoneappbackend.model.ExchangeType;
import cz.osu.phoneappbackend.model.ModelConvertor;
import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.dto.conversation.CreateConversationRequest;
import cz.osu.phoneappbackend.dto.conversation.ConversationItem;
import cz.osu.phoneappbackend.model.exception.NotFoundException;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQConsumer;
import cz.osu.phoneappbackend.repository.ConversationRepository;
import cz.osu.phoneappbackend.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ConversationService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConsumer rabbitMQConsumer;
    private final ConversationRepository conversationRepo;
    private final CustomerRepository customerRep;

    public void createConversation(CreateConversationRequest createRequest){
        TopicExchange exchange = new TopicExchange(createRequest.createExchangeName());
        String routingKey = createRequest.getConversationName() + UUID.randomUUID();
        rabbitTemplate.execute(channel -> {
            channel.exchangeDeclare(exchange.getName(), ExchangeType.TOPIC.toString().toLowerCase());
            return null;
        });
        List<Customer> customers = new ArrayList<>();
        createQueues(customers,exchange,routingKey,createRequest);
        Conversation newConversation = new Conversation(
                routingKey,
                createRequest.createExchangeName(),
                createRequest.getConversationName(), customers,
                new ArrayList<>());
        conversationRepo.save(newConversation);
    }

    private void createQueues(List<Customer> customers, TopicExchange exchange, String routingKey, CreateConversationRequest createRequest){
        for(String userName: createRequest.getUserNames()){
            Queue queue = new Queue(createRequest.createCustomerQueue(userName));
            rabbitTemplate.execute(channel -> {
                channel.queueDeclare(queue.getName(), true, false, false, null);
                channel.queueBind(queue.getName(), exchange.getName(), routingKey);
                return null;
            });
            rabbitMQConsumer.createConsumerForQueue(createRequest.createCustomerQueue(userName));
            customers.add(customerRep.findByUserName(userName).orElseThrow(() ->
                    new NotFoundException("User under the name: " + userName + " was not found")));
        }
    }

    public List<ConversationItem> getAllConversations(String customerName){
        return ModelConvertor.convertListConversationToConversationItemList(conversationRepo
                .findAllByCustomers_UserName(customerName)
                .orElseThrow(()-> new NotFoundException("Could not find any conversations for: " + customerName)));
    }

    public Conversation getConversationByRoutingKey(String routingKey) {
        return conversationRepo.findById(routingKey).orElseThrow(() ->new NotFoundException("Could not find conversation"));
    }
    @Transactional
    public ConversationWindow getFullConversationWindow(String routingKey){
        Conversation conversation = getConversationByRoutingKey(routingKey);
        return ModelConvertor.convertConversationToWindow(conversation);
    }
}
