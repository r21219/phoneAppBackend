package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.CustomerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//TODO try to make it simpler
// idea => whenever a message is recieved grab the routingKey and send it to user so he can use it in the request
// will require hashing for security reasons
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final ConnectionFactory connectionFactory;
    private final Map<String, SimpleMessageListenerContainer> listenerContainers = new HashMap<>();

    public void createConsumerForQueue(String queueName) {
        if (!listenerContainers.containsKey(queueName)) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
            container.setQueueNames(queueName);
            container.setMessageListener((MessageListener) message -> {
                Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
                CustomerMessage customerMessage = (CustomerMessage) converter.fromMessage(message);
                System.out.println(String.format("Received message -> %s", customerMessage.getContent()));
            });
            container.start();
            listenerContainers.put(queueName, container);
        }
    }

    public void stopConsumerForQueue(String queueName) {
        SimpleMessageListenerContainer container = listenerContainers.get(queueName);
        if (container != null) {
            container.stop();
            listenerContainers.remove(queueName);
        }
    }
}