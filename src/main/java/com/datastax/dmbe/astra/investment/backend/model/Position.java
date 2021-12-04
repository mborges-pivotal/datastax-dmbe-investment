package com.datastax.dmbe.astra.investment.backend.model;

import java.math.BigDecimal;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * able positions_by_account is designed to efficiently support Q2 (find all
 * positions in an account, order by instrument symbol (asc)). All
 * positions in a particular account can be retrieved from one partition with
 * multiple rows.
 * 
 * @see https://www.datastax.com/learn/data-modeling-by-example/investment-data-model#physical
 * 
 */

@Table( value = "positions_by_account")
public class Position {

    @PrimaryKey
    private PositionKey key;

    private BigDecimal quantity;


    // No args constructor
    public Position() {
    }

    // All args Constructor
    public Position(PositionKey key, BigDecimal quantity) {
        this.key = key;
        this.quantity = quantity;
    }

    // Accessor methods    

    public PositionKey getKey() {
        return key;
    }

    public void setKey(PositionKey key) {
        this.key = key;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    // Object methods

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }



}
