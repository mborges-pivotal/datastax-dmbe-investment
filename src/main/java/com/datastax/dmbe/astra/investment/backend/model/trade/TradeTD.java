package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table( value = "trades_by_a_td")
public class TradeTD extends AbstractTrade implements Serializable {

    @PrimaryKey
    private TradeTypeKey key;

    private String symbol;


    // No args constructor
    public TradeTD() {
    }

    // Accessor Methods

    public TradeTypeKey getKey() {
        return this.key;
    }

    public void setKey(TradeTypeKey key) {
        this.key = key;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    // Object Methods


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TradeTD)) {
            return false;
        }
        TradeTD tradeTD = (TradeTD) o;
        return Objects.equals(key, tradeTD.key) && Objects.equals(symbol, tradeTD.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, symbol);
    }


    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", symbol='" + getSymbol() + "'" +
            "}";
    }


    
}
