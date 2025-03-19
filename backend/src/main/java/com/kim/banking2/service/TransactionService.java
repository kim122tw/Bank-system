package com.kim.banking2.service;

import com.kim.banking2.model.Transfer;
import com.kim.banking2.model.Transaction;

import java.util.List;

public interface TransactionService {

    // 插入一筆交易
    void insertTransaction(Transaction transaction);

    // 根據帳戶ID取得交易紀錄
    List<Transaction> getTransactionsByAccountId(Long accountId);

    
}
