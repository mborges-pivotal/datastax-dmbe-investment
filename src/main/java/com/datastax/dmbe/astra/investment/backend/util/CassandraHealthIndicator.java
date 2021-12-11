package com.datastax.dmbe.astra.investment.backend.util;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.boot.actuate.cassandra.CassandraDriverHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

// MMB - Not sure if this is needed since I added the management.endpoint.health.show-details=ALWAYS property
@Component
public class CassandraHealthIndicator implements HealthIndicator {

    protected final CqlSession cqlSession;
    protected final CassandraOperations cassandraTemplate;

    private final CassandraDriverHealthIndicator cdhi;

    public CassandraHealthIndicator(CqlSession cqlSession, CassandraOperations ops) {
        this.cqlSession = cqlSession;
        this.cassandraTemplate = ops;

        cdhi = new CassandraDriverHealthIndicator(cqlSession);
    }

    @Override
    public Health health() {
        return cdhi.health();
    }

}
