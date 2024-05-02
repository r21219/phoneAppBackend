package cz.osu.phoneappbackend.model.message;

import cz.osu.phoneappbackend.model.conversation.Conversation;
import cz.osu.phoneappbackend.model.customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Conversation conversation;

    private String content;
    private LocalDateTime timeStamp;

}