package cz.osu.phoneappbackend.service;

import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.dto.message.PublishMessageRequest;
import cz.osu.phoneappbackend.model.message.Message;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQConsumer;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQProducer;
import cz.osu.phoneappbackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final RabbitMQConsumer rabbitMQConsumer;
    private final RabbitMQProducer rabbitMQProducer;
    private final ConversationService conversationService;
    private final CustomerService customerService;
    private final MessageRepository messageRepository;

    public List<Message> getMessageForUser(String userName) {
        return rabbitMQConsumer.getMessageForUser(userName);
    }

    public void sendMessage(PublishMessageRequest messageRequest) {
        Conversation conversation = conversationService.getConversationByRoutingKey(messageRequest.getRoutingKey());
        Customer customer = customerService.getCustomerByUserName(messageRequest.getSender());
        Message message = Message.builder()
                .sender(customer)
                .conversation(conversation)
                .content(messageRequest.getContent())
                .timeStamp(LocalDateTime.now())
                .build();
        messageRepository.save(message);
    }
//TODO implement it
    private void sendMessageNotification(){

    }
}
