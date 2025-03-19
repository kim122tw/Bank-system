package com.kim.banking2.service;

import com.kim.banking2.model.User;
import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    Long createUser(User user);
    boolean updateUser(User user);
    boolean updateBalance(Long fromAccount, Long toAccount, Long amount,Long balance);
    boolean deleteUser(Long userId);
}
