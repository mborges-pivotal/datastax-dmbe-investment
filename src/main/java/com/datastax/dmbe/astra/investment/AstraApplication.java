package com.datastax.dmbe.astra.investment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@EnableCassandraRepositories(basePackages = { "com.datastax.dmbe.astra.investment.repository" })
public class AstraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AstraApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		log.info("Move this code to test suite");

	}

}
