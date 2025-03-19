package com.kim.banking2.controller;

import com.kim.banking2.model.Transaction;
import com.kim.banking2.model.User;
import com.kim.banking2.service.TransactionService;
import com.kim.banking2.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    
    private final TransactionService transactionService;

    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    // 查詢單個使用者
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    
    // 查詢使用者是否存在
    @GetMapping("/username/{username}")
    public ResponseEntity<Boolean> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);

        return ResponseEntity.ok(user != null); // 200 OK

    }

    // 查詢所有使用者
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 創建新使用者（統一 JSON 格式請求）
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser( @RequestParam String username, 
            @RequestParam String email, 
            @RequestParam String fullName, 
            @RequestParam String password, 
            @RequestParam String phoneNumber, 
            @RequestParam String payYN) {
    	 try {
             // 檢查帳號是否已存在
             if (userService.getUserByUsername(username) != null) {
                 return ResponseEntity.status(HttpStatus.CONFLICT)
                         .body(Collections.singletonMap("message", "Username already exists"));
             }

             // 設定初始金額
             long amount = "no".equalsIgnoreCase(payYN) ? 0 : 500;

             // 創建新使用者
             User newUser = new User(username, email, fullName, password, phoneNumber, amount);
             long userId = userService.createUser(newUser);

             // 如果金額大於 0，則新增存款交易
             if (amount > 0) {
                 Transaction transaction = new Transaction(userId, "DEPOSIT", amount);
                 transactionService.insertTransaction(transaction);
             }

             return ResponseEntity.status(HttpStatus.CREATED)
                     .body(Collections.singletonMap("message", "User created successfully"));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body(Collections.singletonMap("error", "Internal server error: " + e.getMessage()));
         }
    }

    // 更新使用者
    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            // 調用 service 層的 updateUser 方法來更新資料
            boolean isUpdated = userService.updateUser(user);
            
            if (isUpdated) {
                // 更新成功，返回成功訊息
                return ResponseEntity.ok("User updated successfully");
            } else {
                // 如果更新失敗，返回 400 錯誤
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User update failed");
            }
        } catch (Exception e) {
            // 捕捉異常，並返回 500 錯誤
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    // 刪除使用者
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String id) {
        try {
            User user = userService.getUserById(Long.parseLong(id));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "User not found"));
            }

            userService.deleteUser(Long.parseLong(id));
            return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Internal server error: " + e.getMessage()));
        }
    }
}
