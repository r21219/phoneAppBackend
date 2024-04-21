package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.model.conversation.CreateConversationRequest;
import cz.osu.phoneappbackend.model.CustomerMessage;
import cz.osu.phoneappbackend.model.conversation.CustomerConversationDTO;
import cz.osu.phoneappbackend.model.rabbitMQ.RabbitMQProducer;
import cz.osu.phoneappbackend.service.CustomerService;
import cz.osu.phoneappbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    private final RabbitMQProducer producer;
    private final MessageService messageService;
    private final CustomerService customerService;

    //TODO create a get request for every chat/group that logged in user has
    @PostMapping("/create")
    public ResponseEntity<String> createConversation(@RequestBody  CreateConversationRequest createRequest) {
        messageService.createConversation(createRequest);
        return ResponseEntity.ok("Conversation successfully created");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody CustomerMessage message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/receive/{userName}")
    public ResponseEntity<List<CustomerMessage>> receiveMessages(@PathVariable String userName) {
        return ResponseEntity.ok(messageService.getMessageForUser(userName));
    }

    @GetMapping("/conversation")
    public ResponseEntity<String> getConversation() {
        return null;
    }

    @GetMapping("/conversation/{userName}")
    public ResponseEntity<List<CustomerConversationDTO>> getConversations(@PathVariable String userName) {
        return ResponseEntity.ok(messageService.getAllConversations(userName));
    }

    @GetMapping("/get/users")
    public ResponseEntity<List<String>> getAllUsersList(){
        return ResponseEntity.ok(customerService.getAllUsers());
    }
}