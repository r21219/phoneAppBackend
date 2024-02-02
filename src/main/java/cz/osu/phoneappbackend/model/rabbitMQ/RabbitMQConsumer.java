package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.CustomerMessage;
import cz.osu.phoneappbackend.model.conversation.CustomerConversation;
import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.model.exception.NotFoundException;
import cz.osu.phoneappbackend.repository.CustomerConversationRepository;
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
public class RabbitMQConsumer {
    private final ConnectionFactory connectionFactory;
    private final Map<String, SimpleMessageListenerContainer> listenerContainers = new HashMap<>();
    private final Map<String, List<CustomerMessage>> userMessages = new HashMap<>();
    private final CustomerConversationRepository conversationRepo;

    public void createConsumerForQueue(String queueName) {
        if (!listenerContainers.containsKey(queueName)) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
            container.setQueueNames(queueName);
            container.setMessageListener((MessageListener) message -> {
                Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
                CustomerMessage customerMessage = (CustomerMessage) converter.fromMessage(message);
                handleReceivedMessage(customerMessage);
            });
            container.start();
            listenerContainers.put(queueName, container);
        }
    }

    //FIXME DO NOT USE THIS METHOD IT CAUSES CYCLIC ERROR UNKNOWN FOR NOW
    private void handleReceivedMessage(CustomerMessage message) {
        CustomerConversation conversation = conversationRepo.findByConversationNameAndCustomers_UserName
                        (message.getConversationName(), message.getSender())
                .orElseThrow(() -> new NotFoundException("Conversation " + message.getConversationName() + " could not be found"));
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

    public List<CustomerMessage> getMessageForUser(String userName) {
        return userMessages.getOrDefault(userName, new ArrayList<>());
    }
}