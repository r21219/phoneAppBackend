package cz.osu.phoneappbackend.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String messageId;
    private String message;
    private Date messageDate;
}
