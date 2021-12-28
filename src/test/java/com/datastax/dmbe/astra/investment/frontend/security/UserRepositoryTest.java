package com.datastax.dmbe.astra.investment.frontend.security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveAdminUserTest() {

        String userName = "admin";

        UserCredentials u = UserCredentials.builder().name(userName).password(passwordEncoder.encode(userName)).enabled(true).build();
        Set<String> roles = Stream.of("ADMIN", "USER").collect(Collectors.toCollection(HashSet::new));
        u.setRoles(roles);
        
        userRepo.save(u);
        UserCredentials uRead = userRepo.findById(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        log.info("found {}", uRead);

    }


    
}
