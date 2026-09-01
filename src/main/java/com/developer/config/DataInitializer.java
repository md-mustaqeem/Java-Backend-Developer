package com.developer.config;

import com.developer.entity.User;
import com.developer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // ADMIN USER

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        }

        // NORMAL USER

        if (userRepository.findByUsername("user").isEmpty()) {

            User normalUser = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .role("USER")
                    .build();

            userRepository.save(normalUser);
            System.out.println("Normal user created successfully!");
        }
    }
}