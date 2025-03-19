package com.kim.banking2.model;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private Long balance;

    // Constructor (可選)
    public User() {
    	this.createdAt = LocalDateTime.now();
    }

    public User(String username, String email, String fullName, String password, String phoneNumber, Long balance) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdAt = LocalDateTime.now();
        this.balance = balance;
    }

    // Getter 和 Setter 方法
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    // toString 方法（方便調試時查看物件內容）
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
