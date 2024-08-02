package com.example.PaymentSystem.Service;


import com.example.PaymentSystem.DTO.AuthenticationRequest;
import com.example.PaymentSystem.DTO.RegisterRequest;
import com.example.PaymentSystem.Model.Role;
import com.example.PaymentSystem.Model.User;
import com.example.PaymentSystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);

    }

    public String login(AuthenticationRequest request) {
        userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return "Login successful";


    }
}
