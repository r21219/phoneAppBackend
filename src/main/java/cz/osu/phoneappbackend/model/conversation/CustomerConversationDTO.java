package cz.osu.phoneappbackend.model.conversation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerConversationDTO {
    private String routingKey;
    private String conversationName;
}
