package com.example.jwtdemo.controller;

import com.example.jwtdemo.bean.User;
import com.example.jwtdemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 添加用户信息
 *
 * UsernamePasswordAuthenticationFilter提供了默认的登录接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    // demo就不写service接口了
    @Autowired
    private UserDao userDao;

    /**
     * spring提供的密码加密工具类
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        // 记得注册的时候把密码加密一下
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRole("ROLE_USER");
        User save = userDao.save(user);
        return save.toString();
    }
}
