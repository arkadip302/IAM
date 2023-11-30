package com.tux.iam.config;

import com.tux.iam.entity.Role;
import com.tux.iam.entity.User;
import com.tux.iam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello !!!! Executed ");
        createInitialEntries();
    }
    private void createInitialEntries() throws InterruptedException {

        int maxAttempts = 5;
        int currentAttempt = 1;
        boolean success = false;

        while (currentAttempt <= maxAttempts && !success) {
            try {
                    User entity1 = new User();
                    entity1.setName("Arkadip");
                    entity1.setRole(Role.SUPER_ADMIN);
                    entity1.setPassword(passwordEncoder.encode("Jana@123"));
                    entity1.setEmail("janaarkadip1.jana@gmail.com");
                    userRepository.save(entity1);
                    success = true;
                    System.out.println("User Saved");
            }catch(RuntimeException e){
                System.err.println("Attempt " + currentAttempt + " failed: " + e.getMessage());
                Thread.sleep(1000);
                currentAttempt++;
            }
        }
        if (!success) {
            throw new RuntimeException("Failed to initialize the database after multiple attempts.");
        }

    }
}

