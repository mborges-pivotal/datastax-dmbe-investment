package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table( value = "trades_by_a_sd")
public class TradeSD extends AbstractTrade implements Serializable {

    @PrimaryKey
    private TradeSymbolKey key;

    private String type;


    // No args constructor
    public TradeSD() {
    }

    // Accessor Methods

    public TradeSymbolKey getKey() {
        return this.key;
    }

    public void setKey(TradeSymbolKey key) {
        this.key = key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Object Methods


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TradeSD)) {
            return false;
        }
        TradeSD tradeSD = (TradeSD) o;
        return Objects.equals(key, tradeSD.key) && Objects.equals(type, tradeSD.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

    
}
