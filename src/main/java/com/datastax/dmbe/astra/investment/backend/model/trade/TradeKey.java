package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;


@PrimaryKeyClass
public class TradeKey implements Serializable {

    @PrimaryKeyColumn(name = "account", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String account;

    @PrimaryKeyColumn(name = "trade_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID tradeId;

    // All Args Constructor
    public TradeKey(String account, UUID tradeId) {
        this.account = account;
        this.tradeId = tradeId;
    }

    // Accessor methods

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public UUID getTradeId() {
        return this.tradeId;
    }

    public void setTradeId(UUID tradeId) {
        this.tradeId = tradeId;
    }

    // Object Methods


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TradeKey)) {
            return false;
        }
        TradeKey tradeKey = (TradeKey) o;
        return Objects.equals(account, tradeKey.account) && Objects.equals(tradeId, tradeKey.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, tradeId);
    }

    @Override
    public String toString() {
        return "{" +
            " account='" + getAccount() + "'" +
            ", tradeId='" + getTradeId() + "'" +
            "}";
    }




}
