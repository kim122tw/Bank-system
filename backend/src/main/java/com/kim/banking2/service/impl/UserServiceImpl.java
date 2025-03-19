package com.kim.banking2.service.impl;

import com.kim.banking2.dao.UserMapper;
import com.kim.banking2.exception.DatabaseOperationException;
import com.kim.banking2.exception.DuplicateUsernameException;
import com.kim.banking2.exception.InvalidUserException;
import com.kim.banking2.model.User;
import com.kim.banking2.service.UserService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public Long createUser(User user) {
    	if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        try {
            // 檢查使用者名稱是否已存在
            User existingUser = userMapper.getUserByUsername(user.getUsername());
            if (existingUser != null) {
                throw new DuplicateUsernameException("Username already exists: " + user.getUsername());
            }

            // 插入新用戶
            userMapper.insertUser(user);

            // 獲取並返回新用戶的 ID
            user = getUserByUsername(user.getUsername());
            return user.getUserId();
        } catch (DuplicateUsernameException e) {
            // 可以記錄日誌並將異常重新拋出
            throw e;  // 重複用戶名稱的異常
        } catch (DataAccessException e) {
            // 處理與資料庫操作相關的異常
            throw new DatabaseOperationException("Error occurred while saving user", e);
        } catch (Exception e) {
            // 捕獲其他異常並處理
            throw new RuntimeException("Unexpected error occurred while creating user", e);
        }
    }

    @Override
    public boolean updateUser(User user) {
    	try {
            int updatedRows = userMapper.updateUser(user);
            if (updatedRows == 0) {
                throw new RuntimeException("更新失敗，找不到對應的使用者：" + user.getUsername());
            }
            return true;
        } catch (DataAccessException e) {
        	throw new DatabaseOperationException("資料庫操作失敗，無法更新使用者：" + user.getUsername(), e);
        } catch (Exception e) {
            // 捕獲其他異常並處理
            throw new RuntimeException("Unexpected error occurred while creating user", e);
        }
    }
    
    @Override
    public boolean updateBalance(Long fromAccount, Long toAccount, Long amount, Long balance) {
    	try {
    		Long toBalance = userMapper.getBalanceById(toAccount);
    		Long fromBalance = userMapper.getBalanceById(fromAccount);
            userMapper.updateBalance(fromAccount, fromBalance - amount);
            userMapper.updateBalance(toAccount, toBalance + amount);
            return true;
        } catch (DataAccessException e) {
        	e.printStackTrace();
        	throw new DatabaseOperationException("資料庫操作失敗，無法更新使用者：", e);
        } catch (Exception e) {
            // 捕獲其他異常並處理
        	e.printStackTrace();
            throw new RuntimeException("Unexpected error occurred while creating user", e);
        }
    }

    @Override
    public boolean deleteUser(Long userId) {
    	try {
            int updatedRows = userMapper.deleteUser(userId);
            if (updatedRows == 0) {
                throw new RuntimeException("更新失敗，找不到對應的使用者：" + userId);
            }
            return true;
        } catch (DataAccessException e) {
        	throw new DatabaseOperationException("資料庫操作失敗，無法更新使用者：" + userId, e);
        } catch (Exception e) {
            // 捕獲其他異常並處理
            throw new RuntimeException("Unexpected error occurred while creating user", e);
        }
    }
}
