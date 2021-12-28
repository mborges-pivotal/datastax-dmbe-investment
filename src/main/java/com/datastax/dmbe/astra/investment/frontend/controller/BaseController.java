package com.datastax.dmbe.astra.investment.frontend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BaseController {

   ///////////////////////////////////
    // Helper Methods
    ///////////////////////////////////

     String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
          username = ((UserDetails)principal).getUsername();
        } else {
          username = principal.toString();
        }    

        log.info("username is {}", username);

        return username;
    }
    
}
