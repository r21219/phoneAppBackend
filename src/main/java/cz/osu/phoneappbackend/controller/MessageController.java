package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.model.CreateRequest;
import cz.osu.phoneappbackend.model.CustomerMessage;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQProducer;
import cz.osu.phoneappbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {
    private final RabbitMQProducer producer;
    private final MessageService service;
    @PostMapping("/create")
    public ResponseEntity<String> createConversation(CreateRequest createRequest){
        service.createConversation(createRequest);
        return ResponseEntity.ok("Conversation successfully created");
    }
    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody CustomerMessage message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
    //TODO make dynamic endRoutes
    @GetMapping("/receive")
    public ResponseEntity<String> receiveMessage(){
        return ResponseEntity.ok("THIS METHOD IS YET TO BE DEVELOPED ;) ");
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(){
        return ResponseEntity.ok("THIS METHOD IS YET TO BE DEVELOPED ;) ");
    }
    @PostMapping("/Login")
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("THIS METHOD IS YET TO BE DEVELOPED ;) ");
    }
}