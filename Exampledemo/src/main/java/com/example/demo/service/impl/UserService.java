package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.UserEntity.UserEntity;

@Service
public interface UserService{


	String getAdduser(UserEntity user) throws Exception;


}
