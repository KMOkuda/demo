package com.example.demo.login.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {	
	@Autowired
    ApplicationContext applicationContext;

	@GetMapping("/login")
	public String getLogin(Model model) {
		System.out.println(Arrays.asList(applicationContext.getBeanDefinitionNames()));
		return "login/login";
	}

	@PostMapping("/login")
	public String postLogin(Model model) {
		return "redirect:/home";
	}
}
