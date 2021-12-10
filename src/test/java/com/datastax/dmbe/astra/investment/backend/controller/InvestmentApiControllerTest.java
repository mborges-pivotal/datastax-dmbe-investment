package com.datastax.dmbe.astra.investment.backend.controller;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import com.datastax.dmbe.astra.investment.backend.loader.CoinbaseCsvLoader;
import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.dmbe.astra.investment.backend.model.AccountKey;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.repository.AccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

// import static org.assertj.core.api.Assertions.assertThat;

// https://reflectoring.io/spring-boot-data-jpa-test/
// https://www.baeldung.com/spring-boot-datacassandratest
// https://codemarvels.com/2017/04/21/spring-boot-testing-cassandra-repositories-using-cassandra-unit/
// https://www.baeldung.com/spring-data-cassandra-test-containers
// @RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class InvestmentApiControllerTest {

    @Autowired
    private InvestmentApiController api;

    @Autowired
    private CoinbaseCsvLoader loader;

    @Autowired
    private AccountRepository accountRepo;

    @Test
    void loadCoinbaseCsvFile() throws Exception {

        AccountKey ak1 = new AccountKey("mborges", "001");
        Account account = new Account(ak1, BigDecimal.ZERO, "Marcelo Borges");
        accountRepo.save(account);
        
        InputStream resource = new ClassPathResource("local/coinbase-1.csv").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            List<Trade> trades = loader.mapFile("001", reader);
            
            for(Trade t: trades) {
                api.createTrade("mborges", t);
            }
        }

    }

    @Test
    void deleteAccount() {
        log.info("Deleting all accounts for mborges");
        api.deleteAllAccounts("mborges");
    }

}
