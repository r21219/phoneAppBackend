package cz.osu.phoneappbackend.dto.conversation;

import cz.osu.phoneappbackend.dto.message.MessageDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ConversationWindow {
    private String routingKey;
    private String conversationName;
    private List<MessageDTO> messages;
}
