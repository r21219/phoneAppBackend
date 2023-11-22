package cz.osu.phoneappbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CreateRequest {
    String conversationName;
    List<String> customers;

    public String createExchangeName(){
        return "exchange_" + conversationName;
    }

    public String createCustomerQueue(String customer){
        return "customerQueue_" + customer + "-" + conversationName;
    }
}