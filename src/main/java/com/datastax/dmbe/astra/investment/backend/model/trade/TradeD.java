package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table( value = "trades_by_a_d")
public class TradeD extends AbstractTrade implements Serializable {

    @PrimaryKey
    private TradeKey key;

    private String symbol;
    private String type;


    // No args constructor
    public TradeD() {
    }

    // Accessor Methods

    public TradeKey getKey() {
        return this.key;
    }

    public void setKey(TradeKey key) {
        this.key = key;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
        if (!(o instanceof TradeD)) {
            return false;
        }
        TradeD tradeD = (TradeD) o;
        return Objects.equals(key, tradeD.key) && Objects.equals(symbol, tradeD.symbol) && Objects.equals(type, tradeD.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, symbol, type);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }



    
}
