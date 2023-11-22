package cz.osu.phoneappbackend.service;

import cz.osu.phoneappbackend.model.Customer;
import cz.osu.phoneappbackend.model.CreateRequest;
import cz.osu.phoneappbackend.model.CustomerConversation;
import cz.osu.phoneappbackend.model.ExchangeType;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQConsumer;
import cz.osu.phoneappbackend.repository.CustomerRepository;
import cz.osu.phoneappbackend.repository.CustomerConversationRepository;
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
public class MessageService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConsumer rabbitMQConsumer;
    private final CustomerConversationRepository customerConversationRepo;
    private final CustomerRepository customerRep;
    public void createConversation(CreateRequest createRequest){
        TopicExchange exchange = new TopicExchange(createRequest.createExchangeName());
        String routingKey = createRequest.getConversationName() + UUID.randomUUID();
        rabbitTemplate.execute(channel -> {
            channel.exchangeDeclare(exchange.getName(), ExchangeType.TOPIC.toString());
            return null;
        });
        List<Customer> customers = new ArrayList<>();
        createQueues(customers,exchange,routingKey,createRequest);
        CustomerConversation newCustomerConversation = new CustomerConversation(routingKey,
                createRequest.getConversationName(), customers);
        customerConversationRepo.save(newCustomerConversation);
    }

    private void createQueues(List<Customer> customers, TopicExchange exchange, String routingKey,CreateRequest createRequest){
        for(String customer: createRequest.getCustomers()){
            Queue queue = new Queue(createRequest.createCustomerQueue(customer));
            rabbitTemplate.execute(channel -> {
                channel.queueDeclare(queue.getName(), true, false, false, null);
                channel.queueBind(queue.getName(), exchange.getName(), routingKey);
                return null;
            });
            rabbitMQConsumer.createConsumerForQueue(createRequest.createCustomerQueue(customer));
            customers.add(customerRep.findByUserName(customer));
        }
    }
}
