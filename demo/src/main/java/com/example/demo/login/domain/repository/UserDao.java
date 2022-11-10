package com.example.demo.login.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.login.domain.model.User;

public interface UserDao extends JpaRepository<User, Integer>{
}
