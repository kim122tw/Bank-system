package com.kim.banking2.dao;

import com.kim.banking2.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

	User getUserById(@Param("userId") Long userId);
	User getUserByUsername(@Param("username") String username);  // 新增方法
	Long getBalanceById(@Param("userId") Long userId);  // 新增方法
    List<User> getAllUsers();
    void insertUser(User user);
    int updateUser(User user);
    int updateBalance(@Param("userId") Long userId, @Param("balance") Long balance);
    int deleteUser(@Param("userId") Long userId);
}