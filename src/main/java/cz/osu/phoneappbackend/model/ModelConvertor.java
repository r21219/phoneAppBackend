package cz.osu.phoneappbackend.model;

import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.dto.conversation.CustomerConversationDTO;

import java.util.ArrayList;
import java.util.List;

public class ModelConvertor {
    public static List<CustomerConversationDTO> convertListCustomerConversationToDTO(List<Conversation> conversations){
        List<CustomerConversationDTO> conversationDTOS = new ArrayList<>();
        for (Conversation conversation : conversations){
            conversationDTOS.add(convertCustomerConversationToDTO(conversation));
        }
        return conversationDTOS;
    }

    public static CustomerConversationDTO convertCustomerConversationToDTO(Conversation conversation){
        return CustomerConversationDTO.builder()
                .conversationName(conversation.getConversationName())
                .routingKey(conversation.getRoutingKey())
                .build();
    }
}
