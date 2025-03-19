package com.kim.banking2.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.kim.banking2.dao")
public class MyBatisConfig {

}
