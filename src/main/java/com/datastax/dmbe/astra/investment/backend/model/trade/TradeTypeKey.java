package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class TradeTypeKey implements Serializable {

    @PrimaryKeyColumn(name = "account", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String account;

    @PrimaryKeyColumn(name = "type", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String type;

    @PrimaryKeyColumn(name = "trade_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID tradeId;

    // All args constructor
    public TradeTypeKey(String account, String type, UUID tradeId) {
        this.account = account;
        this.type = type;
        this.tradeId = tradeId;
    }

    // Accessor methods


    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getTradeId() {
        return this.tradeId;
    }

    public void setTradeId(UUID tradeId) {
        this.tradeId = tradeId;
    }

    // Object methods


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TradeTypeKey)) {
            return false;
        }
        TradeTypeKey tradeTypeKey = (TradeTypeKey) o;
        return Objects.equals(account, tradeTypeKey.account) && Objects.equals(type, tradeTypeKey.type) && Objects.equals(tradeId, tradeTypeKey.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, type, tradeId);
    }

    @Override
    public String toString() {
        return "{" +
            " account='" + getAccount() + "'" +
            ", type='" + getType() + "'" +
            ", tradeId='" + getTradeId() + "'" +
            "}";
    }

}
    
