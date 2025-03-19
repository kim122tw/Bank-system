package com.kim.banking2.controller;

import com.kim.banking2.model.Transaction;
import com.kim.banking2.model.Transfer;
import com.kim.banking2.service.TransactionService;
import com.kim.banking2.service.TransferService;
import com.kim.banking2.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransactionService transactionService;
    private final UserService userService;

    public TransferController(TransferService transferService, TransactionService transactionService
    		, UserService userService) {
        this.transferService = transferService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createTransfer(@RequestBody Map<String, Object> transferData) {
    	Long fromAccount = ((Number) transferData.get("fromAccount")).longValue();
    	Long toAccount = ((Number) transferData.get("toAccount")).longValue();
    	Long amount = ((Number) transferData.get("amount")).longValue();
    	Long balance = ((Number) transferData.get("balance")).longValue();
        Transfer transfer = new Transfer(fromAccount, toAccount, amount);
        Transaction transactionFrom = new Transaction(fromAccount, "TRANSFERFROM", amount);
        Transaction transactionTo = new Transaction(toAccount, "TRANSFERTO", amount);

        // 處理轉帳邏輯
        try {
            // 這裡執行轉帳的業務邏輯
            transferService.insertTransfer(transfer); // 進行交易作業
            
            // 進行轉帳作業
            transactionService.insertTransaction(transactionFrom); 
            transactionService.insertTransaction(transactionTo); 
            
            // 更新使用者帳號金額
            userService.updateBalance(fromAccount, toAccount, amount, balance);
            
            return ResponseEntity.ok("Transfer successfully completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transfer failed: " + e.getMessage());
        }
    }

    // 查詢特定帳戶的所有轉帳記錄
    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transfer>> getTransfers(@PathVariable Long accountId) {
        List<Transfer> transfers = transferService.getTransfersByAccountId(accountId);
        if (transfers.isEmpty()) {
            return ResponseEntity.noContent().build(); // 如果沒有資料，回傳 204 No Content
        }
        return ResponseEntity.ok(transfers); // 返回 200 OK 和轉帳列表
    }
}
