package com.datastax.dmbe.astra.investment.frontend.security;


import java.util.Optional;

import com.datastax.astra.sdk.AstraClient;
import com.datastax.stargate.sdk.doc.CollectionClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// https://reflectoring.io/spring-security-password-handling/
// https://github.com/datastax/astra-sdk-java/wiki/Document-API
@Service
public class UserRepository {

    private static final String COLLECTION = "users";

    @Value("${astra.keyspace:stargate}")
    private String NAMESPACE;
    
    private final CollectionClient userColClient;

    @Autowired
    public UserRepository(AstraClient astraClient) {
        userColClient = astraClient.apiStargateDocument().namespace("stargate").collection(COLLECTION);
    }

    public Optional<UserCredentials> findById(String userName) {
        return userColClient.document(userName).find(UserCredentials.class);
    }

    public void save(UserCredentials userCredentials) {
        userColClient.document(userCredentials.getName()).upsert(userCredentials);
    }

}
