package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.dto.message.PublishMessageRequest;
import cz.osu.phoneappbackend.model.message.Message;
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
    private final MessageService messageService;
    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody PublishMessageRequest message) {
        messageService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
    @GetMapping("/receive/{userName}")
    public ResponseEntity<List<Message>> receiveMessages(@PathVariable String userName) {
        return ResponseEntity.ok(messageService.getMessageForUser(userName));
    }
}