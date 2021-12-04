package com.datastax.dmbe.astra.investment;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Taking over the Cassandra Configuration from the Spring Boot Data Cassandra
 * started so we can use the latest driver with the properties to connect to
 * Astra
 * 
 * @see https://docs.spring.io/spring-data/cassandra/docs/1.5.4.RELEASE/reference/html/
 */
@Configuration
public class AstraConfig {

    /** Logger for the class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AstraConfig.class);

    @Value("${bundle}")
    private File cloudSecureBundle;
    
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
        return builder -> builder.withCloudSecureConnectBundle(cloudSecureBundle.toPath());
    }
}
