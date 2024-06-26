package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.dto.conversation.ConversationWindow;
import cz.osu.phoneappbackend.dto.conversation.CreateConversationRequest;
import cz.osu.phoneappbackend.dto.conversation.ConversationItem;
import cz.osu.phoneappbackend.dto.conversation.GetConversationWindow;
import cz.osu.phoneappbackend.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ConversationController {
    private final ConversationService conversationService;
    //TODO create a get request for every chat/group that logged in user has
    @PostMapping("/create")
    public ResponseEntity<String> createConversation(@RequestBody CreateConversationRequest createRequest) {
        conversationService.createConversation(createRequest);
        return ResponseEntity.ok("Conversation successfully created");
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<ConversationItem>> getConversations(@PathVariable String userName) {
        return ResponseEntity.ok(conversationService.getAllConversations(userName));
    }

    @GetMapping("/get")
    public ResponseEntity<ConversationWindow> getConversations(@RequestBody GetConversationWindow getConversationWindow) {
        return ResponseEntity.ok(conversationService.getFullConversationWindow(getConversationWindow.getRoutingKey()));
    }
}
