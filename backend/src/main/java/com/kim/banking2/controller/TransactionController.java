package com.kim.banking2.controller;

import com.kim.banking2.model.Transaction;
import com.kim.banking2.service.TransactionService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 創建交易
    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        transactionService.insertTransaction(transaction);
        return ResponseEntity.ok("Transaction successfully created!"); // 返回 200 OK
    }

    // 查詢特定帳戶的所有交易記錄
    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build(); // 如果沒有交易記錄，返回 204 No Content
        }
        return ResponseEntity.ok(transactions); // 返回 200 OK 和交易列表
    }
    
    @GetMapping("/downloadTransactions")
    public ResponseEntity<byte[]> downloadTransactions(@RequestParam("userId") Long userId) {
        try {
            // 獲取交易紀錄
            List<Transaction> transactions = transactionService.getTransactionsByAccountId(userId);

            // 如果沒有交易紀錄，返回錯誤訊息
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("無交易紀錄".getBytes());
            }

            // 創建 Excel 工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("交易紀錄");

            // 添加標題行
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("交易方式");
            header.createCell(1).setCellValue("交易金額");
            header.createCell(2).setCellValue("交易日期");

            // 填充資料
            int rowNum = 1;
            for (Transaction transaction : transactions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transaction.getTransactionType());
                row.createCell(1).setCellValue(transaction.getAmount());
                row.createCell(2).setCellValue(transaction.getCreatedAt().toString());
            }

            // 將檔案寫入 byte array
            byte[] bytes;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                workbook.close(); // 確保 workbook 釋放資源
                bytes = outputStream.toByteArray();
            }

            // 設定檔案下載標頭，確保檔名包含 .xlsx
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"交易紀錄.xlsx\"");
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("生成 Excel 檔案時出錯，請稍後再試。".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("發生未知錯誤，請稍後再試。".getBytes());
        }


    }
}
