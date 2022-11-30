package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.login.domain.service.UserService;

@Controller
public class ErrorController {
	@Autowired
	UserService userService;

//	@RequestMapping(value = "/403", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.FORBIDDEN, value = HttpStatus.FORBIDDEN)
	public String forbidden(Model model) {
		model.addAttribute("error", "403 error");
		model.addAttribute("message", "ErrorController 403");
		model.addAttribute("status", HttpStatus.FORBIDDEN);
		System.out.println("403");
		return "error/403";
	}
	
//	@RequestMapping(value = "/404", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
	public String notFound(Model model) {
		model.addAttribute("error", "404 error");
		model.addAttribute("message", "ErrorController 404");
		model.addAttribute("status", HttpStatus.NOT_FOUND);
		System.out.println("404");
		return "error/404";
	}

}
