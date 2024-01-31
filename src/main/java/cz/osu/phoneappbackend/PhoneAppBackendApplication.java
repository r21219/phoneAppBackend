package cz.osu.phoneappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneAppBackendApplication {
//TODO
// Create a way for a user to create a conversation and add it into rabbitMQ //DONE needs to be tested
// Create a way for user to receive all the conversations meant for them // Done needs to be tested
// Create a way for a user to send their message to the group
// Create a way for a webSocket to send notification to the group
// Create a way for the other users to receive all the messages from the group in order
    public static void main(String[] args) {
        SpringApplication.run(PhoneAppBackendApplication.class, args);
    }
}