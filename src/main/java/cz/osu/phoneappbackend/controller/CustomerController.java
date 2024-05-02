package cz.osu.phoneappbackend.controller;

import cz.osu.phoneappbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/get/all")
    public ResponseEntity<List<String>> getAllUsersList(){
        return ResponseEntity.ok(customerService.getAllUsers());
    }
}
