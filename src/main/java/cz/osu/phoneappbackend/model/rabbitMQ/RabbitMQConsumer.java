package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.model.message.Message;
import cz.osu.phoneappbackend.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
//TODO needs complete rework
public class RabbitMQConsumer {
    private final ConnectionFactory connectionFactory;
    private final Map<String, SimpleMessageListenerContainer> listenerContainers = new HashMap<>();
    private final Map<String, List<Message>> userMessages = new HashMap<>();
    private final ConversationRepository conversationRepo;

    public void createConsumerForQueue(String queueName) {
        if (!listenerContainers.containsKey(queueName)) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
            container.setQueueNames(queueName);
            container.setMessageListener((MessageListener) message -> {
                Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
                Message customerMessage = (Message) converter.fromMessage(message);
                handleReceivedMessage(customerMessage);
            });
            container.start();
            listenerContainers.put(queueName, container);
        }
    }

    private void handleReceivedMessage(Message message) {
        Conversation conversation = conversationRepo.findByConversationNameAndCustomers_UserName
                        (message.getConversation().getConversationName(), message.getSender().getUsername())
                .orElseThrow();
        Hibernate.initialize(conversation.getCustomers());
        for (Customer customer : conversation.getCustomers()) {
            String userName = customer.getUsername();
            userMessages.computeIfAbsent(userName, k -> new ArrayList<>()).add(message);
        }
    }

    public void stopConsumerForQueue(String queueName) {
        SimpleMessageListenerContainer container = listenerContainers.get(queueName);
        if (container != null) {
            container.stop();
            listenerContainers.remove(queueName);
        }
    }

    public List<Message> getMessageForUser(String userName) {
        return userMessages.getOrDefault(userName, new ArrayList<>());
    }
}
//TODO send Notifications through rabbitMQ aka
// 1.Connect to one ws like notification
// 2. send a message to that one ws (through rabbitMQ)
// 3. profit?