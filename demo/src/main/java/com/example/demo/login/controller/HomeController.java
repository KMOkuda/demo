package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;

	@GetMapping("/home")
	public String getHome(Model model) {

		//コンテンツ部分にホーム画面を表示させるための文字列を登録
		model.addAttribute("contents","login/home::home_contents");

		return "login/homeLayout";
	}
	
	/*
	@GetMapping("/userList")
	public String getUserList(Model model) {
		
	}*/

	@PostMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}
}
