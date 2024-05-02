package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.dto.authentication.AuthenticationResponse;
import cz.osu.phoneappbackend.dto.customer.CustomerRequest;
import cz.osu.phoneappbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
//TODO MAYBE probably not possible due to current implementation
// create a global static class that manages connected users through websocket
// when a user wants write a msg to another user the other user will be found through ws saved in the manager class
//
public class AuthenticationController {
    private final CustomerService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody CustomerRequest customerRequest) {
        AuthenticationResponse response = service.register(customerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody CustomerRequest customerRequest) {
        AuthenticationResponse response = service.login(customerRequest);
        return ResponseEntity.ok(response);
    }
}