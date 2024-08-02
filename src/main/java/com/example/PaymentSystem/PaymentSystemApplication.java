package com.example.PaymentSystem;

import com.example.PaymentSystem.Model.Product;
import com.example.PaymentSystem.Model.Role;
import com.example.PaymentSystem.Model.User;
import com.example.PaymentSystem.Repository.ProductRepository;
import com.example.PaymentSystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentSystemApplication {
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PaymentSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepo, ProductRepository productRepository) {
        return args -> {
            User user = new User();
            user.setFirstName("Nini");
            user.setLastName("Tsiklauri");
            user.setPassword(passwordEncoder.encode("12345678"));
            user.setEmail("nini@gmail.com");
            user.setRole(Role.ADMIN);
            userRepo.save(user);
            Product product = Product.builder()
                    .user(user)
                    .description("mobile balance")
                    .maxAmount(new BigDecimal(100))
                    .minAmount(new BigDecimal(0))
                    .build();
            Product product1 = Product.builder()
                    .user(user)
                    .description("wifi balance")
                    .maxAmount(new BigDecimal(1000))
                    .minAmount(new BigDecimal(0))
                    .build();
            productRepository.save(product1);
            productRepository.save(product);
        };

    }


}

