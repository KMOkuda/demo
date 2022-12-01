package com.example.demo.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
	/**
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

	**/

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String notFound(Model model) {
		model.addAttribute("error", "404 error");
		model.addAttribute("message", "ErrorController 404");
		model.addAttribute("status", HttpStatus.NOT_FOUND);
		System.out.println("404");
		return "error/404";
	}

}
