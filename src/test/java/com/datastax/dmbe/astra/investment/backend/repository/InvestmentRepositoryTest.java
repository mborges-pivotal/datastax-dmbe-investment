package com.datastax.dmbe.astra.investment.backend.repository;

import java.math.BigDecimal;
import java.util.Optional;

import com.datastax.dmbe.astra.investment.AstraConfig;
import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.dmbe.astra.investment.backend.model.AccountKey;
import com.datastax.dmbe.astra.investment.backend.model.Position;
import com.datastax.dmbe.astra.investment.backend.model.PositionKey;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.Import;

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
@DataCassandraTest
// @DataJdbcTest
@Import(AstraConfig.class)
public class InvestmentRepositoryTest {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private PositionRepository positionRepo;

    @Autowired
    private TradeDRepository tradeDRepo;

    @Autowired
    private TradeSDRepository tradeSDRepo;

    @Autowired
    private TradeTDRepository tradeTDRepo;

    @Test
    void createAccounts() {
        AccountKey ak1 = new AccountKey("mborges", "001");
        Account a1 = new Account(ak1, BigDecimal.ZERO, "Marcelo Borges");

        Optional<Account> oAr1 = accountRepo.findById(ak1);
        if (oAr1.isPresent()) {
            log.info("Account key {} found", oAr1.get());
        } else {
            accountRepo.save(a1);
            log.info("Saved account {}", a1);
        }

    }

    @Test
    void createPositions() {
        PositionKey pk1 = new PositionKey("001", "SNAP");
        Position p1 = new Position(pk1, BigDecimal.valueOf(50));

        Position pr1 = positionRepo.findById(pk1).get();
        if (pr1 != null) {
            log.info("Position key {} found", pr1);
        } else {
            pr1 = positionRepo.save(p1);
            log.info("Saved position {}", pr1);
        }

    }

    // Trade ingestion needs to be synchronized across 3 tables
    @Test
    void createTrades() {

        Trade t1 = new Trade();
        t1.setPrice(BigDecimal.valueOf(144L));
        t1.setShares(BigDecimal.TEN);
        t1.setAmount(t1.getShares().multiply(t1.getPrice()));
        t1.setSymbol("SNAP");
        t1.setType("buy");
        t1.setAccount("001");
        t1.setTradeId(Uuids.timeBased());

        // MMB - Review the best way to ingest data here.
        tradeDRepo.save(mapAsTradeD(t1));
        tradeSDRepo.save(mapAsTradeSD(t1));
        tradeTDRepo.save(mapAsTradeTD(t1));
    }

    @Test
    void deleteAccount() {
    }

}
