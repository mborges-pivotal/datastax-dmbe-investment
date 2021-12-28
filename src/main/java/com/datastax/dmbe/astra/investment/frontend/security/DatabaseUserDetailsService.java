package com.datastax.dmbe.astra.investment.frontend.security;


import com.datastax.astra.sdk.AstraClient;
import com.datastax.stargate.sdk.doc.CollectionClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// https://reflectoring.io/spring-security-password-handling/
// https://github.com/datastax/astra-sdk-java/wiki/Document-API
@Slf4j
@AllArgsConstructor
@Service
public class DatabaseUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

    private final UserDetailsMapper userDetailsMapper;
    private final UserRepository userRepo;

    // UserDetailsService

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("User {} attempting to log in", userName);
        UserCredentials user = userRepo.findById(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return userDetailsMapper.toUserDetails(user);
    }

    // UserDetailsService

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        String userName = user.getUsername();
        UserCredentials userCredentials = userRepo.findById(userName).get();
        userCredentials.setPassword(newPassword);
        userRepo.save(userCredentials);
        return userDetailsMapper.toUserDetails(userCredentials);
    }

}
