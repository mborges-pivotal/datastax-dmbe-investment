package com.datastax.dmbe.astra.investment.frontend.security;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.everit.json.schema.TrueSchema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCredentials {

    private String name;
    private String password;
    private boolean enabled;

    private Set<String> roles;
    
}
