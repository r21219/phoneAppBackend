package cz.osu.phoneappbackend.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerMessage {
    private String sender;
    private String conversationName;
    private String content;
    private LocalDateTime timeStamp;
}