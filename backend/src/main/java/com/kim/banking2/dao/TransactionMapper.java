package com.kim.banking2.dao;

import com.kim.banking2.model.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TransactionMapper {
    void insertTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccountId(@Param("accountId") Long accountId);
}
