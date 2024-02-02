package cz.osu.phoneappbackend.model.conversation;

import cz.osu.phoneappbackend.model.customer.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerConversation {
    @Id
    private String routingKey;
    private String topicName;
    private String conversationName;
    @ManyToMany
    private List<Customer> customers;
}