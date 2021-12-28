package com.datastax.dmbe.astra.investment.frontend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    UserDetails toUserDetails(UserCredentials userCredentials) {

        return User.withUsername(userCredentials.getName())
                .password(userCredentials.getPassword())
                .roles(userCredentials.getRoles().toArray(String[]::new))
                .build();
    }

}
