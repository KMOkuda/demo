package com.example.demo.login.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_user")
public class User {
	@Id
	private String userId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private Date birthday;
	
	@Column(nullable = false)
	private int age;
	
	@Column(nullable = false)
	private boolean marriage;
	
	@Column(nullable = false)
	private String role;
}
