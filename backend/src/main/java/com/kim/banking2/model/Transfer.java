package com.kim.banking2.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Transfer {
    private Long transferId;
    private Long fromAccount;
    private Long toAccount;
    private Long amount;
    private Timestamp createdAt;

    // Constructor
    public Transfer() {}

    public Transfer(Long fromAccount, Long toAccount, Long amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

}
