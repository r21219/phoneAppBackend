package cz.osu.phoneappbackend.model.conversation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateConversationRequest {
    String conversationName;
    List<String> userNames;

    public String createExchangeName() {
        return "exchange_" + conversationName;
    }

    public String createCustomerQueue(String userName) {
        return "customerQueue_" + userName + "-" + conversationName;
    }
}