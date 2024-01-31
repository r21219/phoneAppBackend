package cz.osu.phoneappbackend.model;

import cz.osu.phoneappbackend.model.conversation.CustomerConversation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private Long customerId;
    private String userName;
    private String password;
    @ManyToMany(mappedBy = "customers")
    private List<CustomerConversation> conversations;
}