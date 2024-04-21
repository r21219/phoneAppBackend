package cz.osu.phoneappbackend.service;

import cz.osu.phoneappbackend.config.jwt.JwtService;
import cz.osu.phoneappbackend.model.authentication.AuthenticationResponse;
import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.model.customer.CustomerRequest;
import cz.osu.phoneappbackend.model.customer.Role;
import cz.osu.phoneappbackend.model.exception.AlreadyExistsException;
import cz.osu.phoneappbackend.model.exception.UnAuthorizedException;
import cz.osu.phoneappbackend.repository.CustomerRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Argon2 argon2 = Argon2Factory.create();

    public AuthenticationResponse register(CustomerRequest customerRequest) {
        if (customerRepo.findByUserName(customerRequest.getName()).isPresent()) {
            throw new AlreadyExistsException("User by that name already exists");
        }
        var user = Customer.builder()
                .userName(customerRequest.getName())
                .password(argon2.hash(2, 65536, 1, customerRequest.getPassword().toCharArray()))
                .role(Role.USER)
                .build();
        customerRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(CustomerRequest customerRequest) {
        var user = customerRepo.findByUserName(customerRequest.getName())
                .orElseThrow();
        if (!argon2.verify(user.getPassword(), customerRequest.getPassword().toCharArray())) {
            throw new UnAuthorizedException("Invalid password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerRequest.getName(),
                        customerRequest.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<String> getAllUsers(){
        return customerRepo.getAllCustomerNames();
    }
}
