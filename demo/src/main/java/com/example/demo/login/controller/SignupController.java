package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;

@Controller
public class SignupController {

	private Map<String, String> radioMarriage;

	private Map<String, String> initRadioMarriage(){
		Map<String, String> radio = new LinkedHashMap<String, String>();

		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;
	}

	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, BindingResult bindingResult, Model model) {
		radioMarriage = initRadioMarriage();

		model.addAttribute("radioMarriage", radioMarriage);

		return "login/signup";
	}

	@PostMapping("/signup")
	public String postSignUp(Model model) {
		return "redirect:/login";
	}
}
