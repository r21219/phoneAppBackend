package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.model.MQConfig;
import cz.osu.phoneappbackend.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessagePublisher {
    private final RabbitTemplate template;
    @PostMapping("/publish")
    public String publishMessage(@RequestBody Message message){
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,MQConfig.ROUTING_KEY, message);

        return "Message Published";
    }
}
