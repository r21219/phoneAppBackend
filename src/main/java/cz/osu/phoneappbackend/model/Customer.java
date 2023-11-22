package cz.osu.phoneappbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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