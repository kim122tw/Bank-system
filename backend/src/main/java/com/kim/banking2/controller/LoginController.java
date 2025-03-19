package com.kim.banking2.controller;

import com.kim.banking2.model.User;
import com.kim.banking2.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")  // 允許前端請求
@RestController  // 讓這個 Controller 返回 JSON
public class LoginController {

    @Autowired
    private UserService userService;

    // 處理 Vue 的 JSON 登入請求
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        System.out.println("收到登入請求: " + user.getUsername());
        // 準備回應
        Map<String, Object> response = new HashMap<>();
        // 查詢用戶
        User dbUser = userService.getUserByUsername(user.getUsername());

        if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", dbUser);  // 設置 session
            response.put("message", "登入成功！");
            response.put("accountExist", "true");
            response.put("user", dbUser); // 回傳用戶資料
            
            return ResponseEntity.ok(response);
        } else {
        	response.put("message", "帳號或密碼錯誤！");
            response.put("accountExist", "false");
            
            return ResponseEntity.ok(response);
        }
    }
}
