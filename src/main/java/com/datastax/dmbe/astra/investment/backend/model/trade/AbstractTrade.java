package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AbstractTrade {

    private BigDecimal shares;
    private BigDecimal price;
    private BigDecimal amount;

    // Accessor methods

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
        if (!(o instanceof AbstractTrade)) {
            return false;
        }
        AbstractTrade abstractTrade = (AbstractTrade) o;
        return Objects.equals(shares, abstractTrade.shares) && Objects.equals(price, abstractTrade.price) && Objects.equals(amount, abstractTrade.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shares, price, amount);
    }

    @Override
    public String toString() {
        return "{" +
            " shares='" + getShares() + "'" +
            ", price='" + getPrice() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }


}
