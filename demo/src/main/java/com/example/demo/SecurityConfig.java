package com.example.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	AuthenticationEntryPoint authenticationEntryPoint() {
	    return new SimpleAuthenticationEntryPoint();
	}

	public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

	    @Override
	    public void commence(HttpServletRequest request,
	                         HttpServletResponse response,
	                         AuthenticationException exception) throws IOException, ServletException {
	        if (response.isCommitted()) {
	            System.out.println("Response has already been committed.");
	            return;
	        }
	        System.out.println("com sent error.");
	        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
	        response.sendRedirect("/error");
	    }

	}


	public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

	    @Override
	    public void handle(HttpServletRequest request,
	                       HttpServletResponse response,
	                       AccessDeniedException exception) throws IOException, ServletException {

	    	if (response.isCommitted()) {
	            System.out.println("Response has already been committed.");
	            return;
	        }
	    	response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
	        response.sendRedirect("/error");

	    }


	}

	AccessDeniedHandler accessDeniedHandler() {
		return new SimpleAccessDeniedHandler();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authz -> authz
				.mvcMatchers("/webjars/**", "/css/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/bbbb").authenticated()//なぜか用意したエラーページが出る！
				);

		http.csrf().disable();

//		http.exceptionHandling()
//		.authenticationEntryPoint(authenticationEntryPoint());
//		.accessDeniedHandler(accessDeniedHandler());
//		.accessDeniedPage("/error");

		return http.build();
	}
}
