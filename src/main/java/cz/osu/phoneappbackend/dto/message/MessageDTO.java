package cz.osu.phoneappbackend.dto.message;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class MessageDTO {
    private String sender;
    private String content;
    private LocalDateTime timeStamp;
}
