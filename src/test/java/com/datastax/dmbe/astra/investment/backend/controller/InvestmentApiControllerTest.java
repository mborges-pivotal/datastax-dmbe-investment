package com.datastax.dmbe.astra.investment.backend.controller;

import java.math.BigDecimal;
import java.util.Optional;

import com.datastax.dmbe.astra.investment.AstraConfig;
import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.dmbe.astra.investment.backend.model.AccountKey;
import com.datastax.dmbe.astra.investment.backend.model.Position;
import com.datastax.dmbe.astra.investment.backend.model.PositionKey;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.repository.AccountRepository;
import com.datastax.dmbe.astra.investment.backend.repository.PositionRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeSDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeTDRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeSD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeTD;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void deleteAccount() {
        log.info("Deleting all accounts for mborges");
        
        api.deleteAllAccounts("mborges");
    }

}
