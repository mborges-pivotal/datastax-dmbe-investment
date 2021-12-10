package com.datastax.dmbe.astra.investment.backend.model;

import com.datastax.dmbe.astra.investment.backend.util.DateTimeUtils;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class Trade {

    private String account;
    private String symbol;
    private String type;
    private UUID tradeId;
    private BigDecimal shares;
    private BigDecimal price;
    private BigDecimal amount;

    // Calculated methods
    public ZonedDateTime getDate() {
        return DateTimeUtils.timeBasedUuidToDate(tradeId);    
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

    public BigDecimal getShares() {
        return this.shares;
    }

    public void setShares(BigDecimal shares) {
        this.shares = shares;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Object Methods


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Trade)) {
            return false;
        }
        Trade trade = (Trade) o;
        return Objects.equals(account, trade.account) && Objects.equals(symbol, trade.symbol) && Objects.equals(type, trade.type) && Objects.equals(tradeId, trade.tradeId) && Objects.equals(shares, trade.shares) && Objects.equals(price, trade.price) && Objects.equals(amount, trade.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, symbol, type, tradeId, shares, price, amount);
    }

    @Override
    public String toString() {
        return "{" +
            " account='" + getAccount() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", type='" + getType() + "'" +
            ", tradeId='" + getTradeId() + "'" +
            ", shares='" + getShares() + "'" +
            ", price='" + getPrice() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }

    // Helper methods

    
    
}
