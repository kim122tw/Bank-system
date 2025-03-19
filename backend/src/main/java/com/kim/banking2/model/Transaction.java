package com.kim.banking2.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Transaction {
    private Long transactionId;
    private Long accountId;
    private String transactionType;  // "DEPOSIT"(存款), "WITHDRAW"(取款), "TRANSFERTO"（轉帳轉出）, "TRANSFERFROM"（轉帳轉入）
    private Long amount;
    private Timestamp createdAt;

    // Constructor
    public Transaction() {}

    public Transaction(Long accountId, String transactionType, Long amount) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

}
