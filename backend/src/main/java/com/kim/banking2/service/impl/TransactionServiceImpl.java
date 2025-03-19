package com.kim.banking2.service.impl;

import com.kim.banking2.dao.TransferMapper;
import com.kim.banking2.exception.DatabaseOperationException;
import com.kim.banking2.dao.TransactionMapper;
import com.kim.banking2.model.Transaction;
import com.kim.banking2.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public void insertTransaction(Transaction transaction) {
        try {
            transactionMapper.insertTransaction(transaction);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("交易記錄插入失敗，資料庫錯誤：" + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("交易記錄插入失敗，發生未知錯誤：" + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionMapper.getTransactionsByAccountId(accountId);
       
    }
}
