package cz.osu.phoneappbackend.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMessage {
    private String sender;
    private String receiver;
    private String group;
    private String content;
    private LocalDateTime timeStamp;

    public String generateRoutingKeyForConversation(){
        return sender + receiver + UUID.randomUUID();
    }
    public String generateRoutingKeyForGroup(){
        return group + UUID.randomUUID();
    }
}
