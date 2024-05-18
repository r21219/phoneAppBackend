package cz.osu.phoneappbackend.model;

import cz.osu.phoneappbackend.dto.conversation.ConversationWindow;
import cz.osu.phoneappbackend.dto.message.MessageDTO;
import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.dto.conversation.ConversationItem;
import cz.osu.phoneappbackend.model.message.Message;

import java.util.ArrayList;
import java.util.List;

public class ModelConvertor {
    public static List<ConversationItem> convertListConversationToConversationItemList(List<Conversation> conversations){
        List<ConversationItem> conversationDTOS = new ArrayList<>();
        for (Conversation conversation : conversations){
            conversationDTOS.add(convertConversationToConversationItem(conversation));
        }
        return conversationDTOS;
    }

    public static ConversationItem convertConversationToConversationItem(Conversation conversation){
        return ConversationItem.builder()
                .conversationName(conversation.getConversationName())
                .routingKey(conversation.getRoutingKey())
                .build();
    }

    public static ConversationWindow convertConversationToWindow(Conversation conversation){
        return ConversationWindow.builder()
                .routingKey(conversation.getRoutingKey())
                .conversationName(conversation.getConversationName())
                .messages(convertListMessagesToDTO(conversation.getMessages()))
                .build();
    }

    public static MessageDTO convertMessageToDTO(Message message){
        return MessageDTO.builder()
                .sender(message.getSender().getUsername())
                .timeStamp(message.getTimeStamp())
                .content(message.getContent())
                .build();
    }

    public static List<MessageDTO> convertListMessagesToDTO(List<Message> messages){
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messages){
            messageDTOS.add(convertMessageToDTO(message));
        }
        return messageDTOS;
    }
}