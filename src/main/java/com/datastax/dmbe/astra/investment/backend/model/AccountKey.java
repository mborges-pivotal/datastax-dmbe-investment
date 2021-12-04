package com.datastax.dmbe.astra.investment.backend.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class AccountKey implements Serializable {

    @PrimaryKeyColumn(name = "username", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userName;

    @PrimaryKeyColumn(name = "account_number", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String accountNumber;

    // All args constructor
    public AccountKey(String userName, String accountNumber) {
        this.userName = userName;
        this.accountNumber = accountNumber;
    }

    // Accessor methods

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Object methods

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AccountKey)) {
            return false;
        }
        AccountKey accountKey = (AccountKey) o;
        return Objects.equals(userName, accountKey.userName) && Objects.equals(accountNumber, accountKey.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, accountNumber);
    }

    @Override
    public String toString() {
        return "{" +
                " userName='" + getUserName() + "'" +
                ", accountNumber='" + getAccountNumber() + "'" +
                "}";
    }

}
