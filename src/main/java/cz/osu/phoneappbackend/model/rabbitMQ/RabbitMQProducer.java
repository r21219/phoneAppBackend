package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.CustomerMessage;
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
//TODO When app is turned off make ensurance that the user will be subscribed to the conversation again
    private final RabbitMQConsumer rabbitMQConsumer;
//TODO split into 2 methods one for creating queues
//  second one for sending a message

    //TODO fetch data from database instead
    public void sendMessage(CustomerMessage message){
        //FIXME uncomment
        //CustomerConversation conversation = customerConversationRepo.findByNameAndCustomers_UserName(message.getReceiver(), message.getSender());
        //rabbitTemplate.convertAndSend(conversation.getTopicName(), conversation.getRoutingKey(), message.getContent());
        messagingTemplate.convertAndSend("/topic/msg", message);
    }
}