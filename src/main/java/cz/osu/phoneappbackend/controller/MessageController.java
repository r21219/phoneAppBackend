package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.model.UserMessage;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO
// Todo queue for groups = groupName+userName + routingKey = groupName + UUID
// Todo queue for direct = userName+OtherUserName  + routingKey = bothUsers + UUID
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {
    private final RabbitMQProducer producer;

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody UserMessage message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}
