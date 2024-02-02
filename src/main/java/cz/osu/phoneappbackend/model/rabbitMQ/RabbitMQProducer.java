package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.model.CustomerMessage;
import cz.osu.phoneappbackend.model.ModelConvertor;
import cz.osu.phoneappbackend.model.conversation.CustomerConversation;
import cz.osu.phoneappbackend.model.exception.NotFoundException;
import cz.osu.phoneappbackend.repository.CustomerConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;
    private final CustomerConversationRepository customerConversationRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitMQConsumer rabbitMQConsumer;

    public void sendMessage(CustomerMessage message){
        CustomerConversation conversation = customerConversationRepo
                        .findByConversationNameAndCustomers_UserName(message.getConversationName(), message.getSender())
                        .orElseThrow(()-> new NotFoundException("Couldn't find conversation under the name: " + message.getConversationName() ));
            rabbitTemplate.convertAndSend(conversation.getTopicName(), conversation.getRoutingKey(), message);
        for (Customer customer : conversation.getCustomers()) {
            messagingTemplate.convertAndSendToUser(customer.getUsername(), "/topic/msg/" + customer.getUsername(),
                    ModelConvertor.convertCustomerConversationToDTO(conversation));
        }
    }
}