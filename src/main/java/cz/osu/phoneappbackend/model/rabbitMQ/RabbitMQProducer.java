package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.ExchangeType;
import cz.osu.phoneappbackend.model.CustomerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;
//TODO When app is turned off make ensurance that the user will be subscribed to the conversation again
    private final RabbitMQConsumer rabbitMQConsumer;
//TODO split into 2 methods one for creating queues
//  second one for sending a message

    //TODO fetch data from database instead
    public void sendMessage(CustomerMessage message){
        rabbitTemplate.convertAndSend("Insert real key here", message.getContent());
            rabbitTemplate.convertAndSend(ExchangeType.FANOUT.toString());
    }
}