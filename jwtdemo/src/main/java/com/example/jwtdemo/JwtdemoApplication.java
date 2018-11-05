package com.example.jwtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 * 所以现在认证的路径统一了一下也是挺好的~看起来相当舒服了
 注册：/auth/register
 登录：/auth/login
 *
 *
 */
@SpringBootApplication
public class JwtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtdemoApplication.class, args);
	}
}
