package com.datastax.dmbe.astra.investment;

import java.util.Optional;

import com.datastax.astra.sdk.AstraClient;
import com.datastax.astra.sdk.databases.domain.Database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import lombok.extern.slf4j.Slf4j;

// https://github.com/DataStax-Examples/java-cassandra-driver-from3x-to4
// https://github.com/DataStax-Examples
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@EnableCassandraRepositories(basePackages = { "com.datastax.dmbe.astra.investment.repository" })
// @SpringBootApplication(exclude = {
// 		CassandraDataAutoConfiguration.class,
// 		CassandraAutoConfiguration.class
// })
public class AstraApplication implements CommandLineRunner {

	public static void main(String[] args) {

		try (AstraClient astraClient = configureAstraClient()) {
			Optional<Database> db = astraClient.apiDevopsDatabases().databaseByName("learning").find();
			System.out.println("databaseId=" + db.get().getId());
			System.out.println("databaseRegion=" + db.get().getInfo().getRegion());
			System.out.println("keyspace=" + db.get().getInfo().getKeyspace());
		  }
		  		
		SpringApplication.run(AstraApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		log.info("Move this code to test suite");

	}

	public static AstraClient configureAstraClient() {
		return AstraClient.builder()
		  // Provide the value of your token
		  .withToken("AstraCS:rMALjOzrZdZbrcGxoLIvOrJk:91d5932209b12daee0492c107232d9263d79d8a331c50b9bf880b9e78f77f56c")
		  .build();
	  }	

}
