package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.model.authentication.AuthenticationResponse;
import cz.osu.phoneappbackend.model.customer.CustomerRequest;
import cz.osu.phoneappbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    private final CustomerService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(service.register(customerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(service.login(customerRequest));
    }
}
