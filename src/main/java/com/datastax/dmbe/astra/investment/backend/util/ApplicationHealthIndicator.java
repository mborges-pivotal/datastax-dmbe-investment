package com.datastax.dmbe.astra.investment.backend.util;

import com.datastax.oss.driver.api.core.CqlSession;
import java.math.BigInteger;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.health.Health;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlTemplate;

// https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#reference
// https://www.baeldung.com/spring-boot-health-indicators
@Component
@Slf4j
public class ApplicationHealthIndicator implements HealthIndicator {

    protected final CqlSession cqlSession;
    protected final CassandraOperations cassandraTemplate;
    protected CqlTemplate cqlTemplate;

    public ApplicationHealthIndicator(CqlSession cqlSession, CassandraOperations ops) {
        this.cqlSession = cqlSession;
        this.cassandraTemplate = ops;
    }
    
    @Override
    public Health health() {

        cqlTemplate = new CqlTemplate(cqlSession);
        
        Health.Builder status = Health.up();

        BigInteger accountsCount = cqlTemplate.queryForObject("SELECT COUNT(*) FROM accounts_by_user", BigInteger.class);
        BigInteger positionsCount = cqlTemplate.queryForObject("SELECT COUNT(*) FROM positions_by_account", BigInteger.class);
        
        BigInteger tradesCount1 = cqlTemplate.queryForObject("SELECT COUNT(*) FROM trades_by_a_d", BigInteger.class);
        BigInteger tradesCount2 = cqlTemplate.queryForObject("SELECT COUNT(*) FROM trades_by_a_sd", BigInteger.class);
        BigInteger tradesCount3 = cqlTemplate.queryForObject("SELECT COUNT(*) FROM trades_by_a_td", BigInteger.class);

        if (!(tradesCount1.equals(tradesCount2) && tradesCount2.equals(tradesCount3))) {
            status = Health.unknown();
            log.warn("trades tables are not consistent {},{},{}", tradesCount1, tradesCount2, tradesCount3);
        }

        status
        .withDetail("accounts", accountsCount)
        .withDetail("positions", positionsCount)
        .withDetail("trades", tradesCount1);

        return status.build();
    }
    
}
