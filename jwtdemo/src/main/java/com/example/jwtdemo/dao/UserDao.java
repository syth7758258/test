package com.example.jwtdemo.dao;

import com.example.jwtdemo.bean.User;
import org.springframework.data.repository.CrudRepository;


public interface UserDao extends CrudRepository<User,Integer> {
    User findByUsername(String userName);
}
