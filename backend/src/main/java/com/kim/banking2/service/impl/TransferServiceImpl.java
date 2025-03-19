package com.kim.banking2.service.impl;

import com.kim.banking2.dao.TransferMapper;
import com.kim.banking2.exception.DatabaseOperationException;
import com.kim.banking2.model.Transfer;
import com.kim.banking2.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferMapper transferMapper;

    @Override
    public void insertTransfer(Transfer transfer) {
        try {
            transferMapper.insertTransfer(transfer);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("轉帳失敗，資料庫錯誤：" + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("轉帳失敗，發生未知錯誤：" + e.getMessage(), e);
        }
    }

    @Override
    public List<Transfer> getTransfersByAccountId(Long accountId) {
  
    	return transferMapper.getTransfersByAccountId(accountId);
        
    }
}
