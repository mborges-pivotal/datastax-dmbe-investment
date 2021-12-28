package com.datastax.dmbe.astra.investment.backend.controller;

import com.datastax.astra.sdk.AstraClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class AdminApiController {

    @Autowired
    private AstraClient astraClient;
    
    @GetMapping("/admin/astra")
    public String astraInfo() { 
      return astraClient.apiDevopsOrganizations().organizationId(); 
    }    


    
}
