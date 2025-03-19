package com.kim.banking2.dao;

import com.kim.banking2.model.Transfer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TransferMapper {
    int insertTransfer(Transfer transfer);
    List<Transfer> getTransfersByAccountId(@Param("accountId") Long accountId);
}
