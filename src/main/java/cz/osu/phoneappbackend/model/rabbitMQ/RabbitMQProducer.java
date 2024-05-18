package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.model.message.Message;
import cz.osu.phoneappbackend.model.ModelConvertor;
import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.model.exception.NotFoundException;
import cz.osu.phoneappbackend.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//TODO needs rework to work with conversations instead
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ConversationRepository customerConversationRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitMQConsumer rabbitMQConsumer;

    public void sendMessage(Message message){
        Conversation conversation = customerConversationRepo
                        .findByConversationNameAndCustomers_UserName(message.getConversation().getConversationName(), message.getSender().getUsername())
                        .orElseThrow(()-> new NotFoundException("Couldn't find conversation under the name: " + message.getConversation().getConversationName() ));
            rabbitTemplate.convertAndSend(conversation.getTopicName(), conversation.getRoutingKey(), message);

        for (Customer customer : conversation.getCustomers()) {
            messagingTemplate.convertAndSendToUser(customer.getUsername(), "/topic/msg/" + customer.getUsername(),
                    ModelConvertor.convertConversationToConversationItem(conversation));
        }
    }
}