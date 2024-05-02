package cz.osu.phoneappbackend.dto.message;

import lombok.Data;

@Data
public class PublishMessageRequest {
    private String sender;
    private String routingKey;
    private String content;
}
