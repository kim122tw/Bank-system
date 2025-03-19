package com.kim.banking2.model;

import java.math.BigDecimal;

public class Account {
    private Long accountId;
    private String accountNumber;
    private BigDecimal balance;

    // Constructor
    public Account() {}

    public Account(Long accountId, String accountNumber, BigDecimal balance) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
