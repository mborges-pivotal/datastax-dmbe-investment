package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class TradeSymbolKey implements Serializable {

    @PrimaryKeyColumn(name = "account", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String account;

    @PrimaryKeyColumn(name = "symbol", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String symbol;

    @PrimaryKeyColumn(name = "trade_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID tradeId;

    // All args constructor
    public TradeSymbolKey(String account, String symbol, UUID tradeId) {
        this.account = account;
        this.symbol = symbol;
        this.tradeId = tradeId;
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
        if (!(o instanceof TradeSymbolKey)) {
            return false;
        }
        TradeSymbolKey tradeSymbolKey = (TradeSymbolKey) o;
        return Objects.equals(account, tradeSymbolKey.account) && Objects.equals(symbol, tradeSymbolKey.symbol) && Objects.equals(tradeId, tradeSymbolKey.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, symbol, tradeId);
    }

    @Override
    public String toString() {
        return "{" +
            " account='" + getAccount() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", tradeId='" + getTradeId() + "'" +
            "}";
    }


    
}
