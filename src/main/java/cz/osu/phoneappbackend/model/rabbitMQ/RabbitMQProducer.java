package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.ExchangeType;
import cz.osu.phoneappbackend.model.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConsumer rabbitMQConsumer;

    public void sendMessage(UserMessage message){
        if (message.getGroup().isBlank() || message.getGroup().isEmpty()){
            String senderQueueName = "userQueue_" + message.getSender() + message.getReceiver();
            String receiverQueueName = "userQueue_" + message.getReceiver() + message.getSender();
            Queue senderQueue = new Queue(senderQueueName);
            Queue receiverQueue = new Queue(receiverQueueName);
            rabbitTemplate.execute(channel -> {
                channel.queueDeclare(senderQueue.getName(), true, false, false, null);
                channel.queueDeclare(receiverQueue.getName(), true, false, false, null);
                return null;
            });
            rabbitTemplate.convertAndSend(ExchangeType.DIRECT.toString(),message.generateRoutingKeyForConversation(),message.getContent());
            rabbitMQConsumer.createConsumerForQueue(receiverQueueName);
        }
        else {
            rabbitTemplate.convertAndSend(ExchangeType.FANOUT.toString(),message.generateRoutingKeyForGroup(),message.getContent());
        }
    }
}
