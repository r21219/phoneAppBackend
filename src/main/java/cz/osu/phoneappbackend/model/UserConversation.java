package cz.osu.phoneappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserConversation {
    private String routingKey;
    private AppUser user1;
    private AppUser user2;
}
