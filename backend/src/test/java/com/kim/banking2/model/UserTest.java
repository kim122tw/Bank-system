package com.kim.banking2.model;

import com.kim.banking2.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

@SpringBootTest
class UserTest {

    @Autowired
    private UserMapper userMapper;  // 自動注入 MyBatis Mapper

    private User testUser;  // 測試用的 User 物件

    @BeforeEach
    void setUp() {
        // 建立測試用的 User 物件
        testUser = new User();
        testUser.setUserId(999L);  // 設定一個不會衝突的 ID
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFullName("Test User");
        testUser.setPassword("testpassword");
        testUser.setPhoneNumber("0912345678");
        testUser.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testInsertUser() {
        // 1. 插入測試用戶
        userMapper.insertUser(testUser);

        // 2. 從資料庫查詢該用戶
        User retrievedUser = userMapper.getUserByUsername(testUser.getUsername());

        // 3. 驗證插入是否成功
        assertNotNull(retrievedUser, "查詢結果不應為 null");
        assertEquals(testUser.getUsername(), retrievedUser.getUsername());
        assertEquals(testUser.getEmail(), retrievedUser.getEmail());
        assertEquals(testUser.getFullName(), retrievedUser.getFullName());
        assertEquals(testUser.getPassword(), retrievedUser.getPassword());
        assertEquals(testUser.getPhoneNumber(), retrievedUser.getPhoneNumber());

        // 顯示結果到 Console
        System.out.println("資料庫插入成功，新增的 User 資料如下：");
        System.out.println("User ID: " + retrievedUser.getUserId());
        System.out.println("Username: " + retrievedUser.getUsername());
        System.out.println("Email: " + retrievedUser.getEmail());
        System.out.println("Full Name: " + retrievedUser.getFullName());
        System.out.println("Phone Number: " + retrievedUser.getPhoneNumber());
        System.out.println("Created At: " + retrievedUser.getCreatedAt());
    }
}
