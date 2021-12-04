package com.datastax.dmbe.astra.investment.backend.model;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Table accounts_by_user is designed to have one partition per user, where each
 * row in a partition corresponds to a user account. Column name is a static
 * column as it describes a user who is uniquely identified by the table
 * partition key.
 * 
 * @see https://www.datastax.com/learn/data-modeling-by-example/investment-data-model#physical
 */

@Table( value = "accounts_by_user")
public class Account {

    @PrimaryKey
    private AccountKey key;
    
    @Column("cash_balance")
    private BigDecimal cashBalance;

    private String name;

    // No args constructor
    public Account() {
    }

    // All args constructor
    public Account(AccountKey key, BigDecimal cashBalance, String name) {
        this.key = key;
        this.cashBalance = cashBalance;
        this.name = name;
    }

    // Accessor methods

    public AccountKey getKey() {
        return this.key;
    }

    public void setKey(AccountKey key) {
        this.key = key;
    }

    public BigDecimal getCashBalance() {
        return this.cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Object methods

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", cashBalance='" + getCashBalance() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(key, account.key) && Objects.equals(cashBalance, account.cashBalance) && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, cashBalance, name);
    }


    
}
