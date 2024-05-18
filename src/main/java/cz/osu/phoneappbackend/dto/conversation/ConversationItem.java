package cz.osu.phoneappbackend.dto.conversation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationItem {
    private String routingKey;
    private String conversationName;

}
