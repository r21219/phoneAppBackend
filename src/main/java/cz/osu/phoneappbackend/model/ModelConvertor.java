package cz.osu.phoneappbackend.model;

import cz.osu.phoneappbackend.model.conversation.CustomerConversation;
import cz.osu.phoneappbackend.model.conversation.CustomerConversationDTO;

import java.util.ArrayList;
import java.util.List;

public class ModelConvertor {
    public static List<CustomerConversationDTO> convertListCustomerConversationToDTO(List<CustomerConversation> customerConversations){
        List<CustomerConversationDTO> conversationDTOS = new ArrayList<>();
        for (CustomerConversation customerConversation : customerConversations){
            conversationDTOS.add(convertCustomerConversationToDTO(customerConversation));
        }
        return conversationDTOS;
    }

    public static CustomerConversationDTO convertCustomerConversationToDTO(CustomerConversation customerConversation){
        return CustomerConversationDTO.builder()
                .name(customerConversation.getConversationName())
                .routingKey(customerConversation.getRoutingKey())
                .build();
    }
}
