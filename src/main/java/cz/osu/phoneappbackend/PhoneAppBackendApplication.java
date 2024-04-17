package cz.osu.phoneappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneAppBackendApplication {
//TODO
// Create a way for a user to create a conversation and add it into rabbitMQ //DONE needs to be tested
// Create a way for user to receive all the conversations meant for them // DONE needs to be tested
// Create a way for a user to send their message to the group //DONE needs to be tested
// Create a way for the other users to receive all the messages from the group in order
// Create a way for a webSocket to send notification to the group
// REWORK of  ^
// 1. User registers or logs in and immediately connects to a WS //DONE
// 2. User will see a menu for the chats
// 2A: User will be able to create a conversation with a user/users (FE will fetch a list of users for easier use)
// 2B: The menu itself will show all the active chats or current ones
// 2C: Once user clicks on a conversation a chat box will appear on the right side with a box for chatting and the chat above it


    public static void main(String[] args) {
        SpringApplication.run(PhoneAppBackendApplication.class, args);
    }
}