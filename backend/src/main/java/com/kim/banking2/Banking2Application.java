package com.kim.banking2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kim.banking2.dao")
public class Banking2Application {

	public static void main(String[] args) {
		SpringApplication.run(Banking2Application.class, args);
	}

}
