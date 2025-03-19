package com.kim.banking2.service;

import com.kim.banking2.dao.TransferMapper;
import com.kim.banking2.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransferService {

	// 插入一筆轉帳紀錄
    void insertTransfer(Transfer transfer);

    // 根據帳戶ID取得轉帳紀錄
    List<Transfer> getTransfersByAccountId(Long accountId);
}
