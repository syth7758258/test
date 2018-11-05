package com.example.jwtdemo.bean;


import lombok.Data;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "tb_user")
@Data
public class User {
    @Id
    @Column(name="ID")
    //自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


}
