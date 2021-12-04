package com.datastax.dmbe.astra.investment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
public class AstraProperties {

    private String bundle;
    private String username;
    private String password;
    private String keyspace;


    public String getBundle() {
        return this.bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyspace() {
        return this.keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }


}
