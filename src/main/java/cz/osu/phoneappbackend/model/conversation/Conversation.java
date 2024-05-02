package cz.osu.phoneappbackend.model.conversation;

import cz.osu.phoneappbackend.model.message.Message;
import cz.osu.phoneappbackend.model.customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conversation {
    @Id
    private String routingKey;
    private String topicName;
    private String conversationName;
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Customer> customers;
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages;
}