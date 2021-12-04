package com.datastax.dmbe.astra.investment.backend.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class PositionKey implements Serializable {

    @PrimaryKeyColumn(name = "account", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String account;

    @PrimaryKeyColumn(name = "symbol", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String symbol;

    // All args constructor
    public PositionKey(String account, String symbol) {
        this.account = account;
        this.symbol = symbol;
    }

    // Accessor methods

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    // Object methods

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PositionKey)) {
            return false;
        }
        PositionKey positionKey = (PositionKey) o;
        return Objects.equals(account, positionKey.account) && Objects.equals(symbol, positionKey.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, symbol);
    }

    @Override
    public String toString() {
        return "{" +
            " account='" + getAccount() + "'" +
            ", symbol='" + getSymbol() + "'" +
            "}";
    }


    
}

