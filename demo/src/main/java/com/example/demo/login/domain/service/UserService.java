package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.login.domain.repository.UserDao;

public class UserService {
	@Autowired
	UserDao dao;
}
