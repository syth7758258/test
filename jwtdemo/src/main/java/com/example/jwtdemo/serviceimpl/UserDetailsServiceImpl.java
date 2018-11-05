package com.example.jwtdemo.serviceimpl;

import com.example.jwtdemo.bean.JwtUser;
import com.example.jwtdemo.bean.User;
import com.example.jwtdemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        return new JwtUser(user);
    }

}
