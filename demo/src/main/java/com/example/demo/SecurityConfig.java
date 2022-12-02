package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	/**
	AuthenticationEntryPoint authenticationEntryPoint() {
	    return new SimpleAuthenticationEntryPoint();
	}

	//未認証のユーザーからのアクセスを拒否した際のエラー応答を行うためのインタフェース
	public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

	    @Override
	    public void commence(HttpServletRequest request,
	                         HttpServletResponse response,
	                         AuthenticationException exception) throws IOException, ServletException {
	        if (response.isCommitted()) {
	            System.out.println("Response has already been committed.");
	            return;
	        }
	        System.out.println("SimpleAuthenticationEntryPoint.");
	        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
	        response.sendRedirect("/error");
	    }

	}

	//認証済みのユーザーからのアクセスを拒否した際のエラー応答を行うためのインタフェース
	public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

	    @Override
	    public void handle(HttpServletRequest request,
	                       HttpServletResponse response,
	                       AccessDeniedException exception) throws IOException, ServletException {

	    	if (response.isCommitted()) {
	            System.out.println("Response has already been committed.");
	            return;
	        }
	    	System.out.println("SimpleAccessDeniedHandler.");
	    	response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
	        response.sendRedirect("/error");

	    }


	}

	AccessDeniedHandler accessDeniedHandler() {
		return new SimpleAccessDeniedHandler();
	}
	**/

	@Autowired
	private DataSource dataSource;

	private static final String USER_SQL = "SELECT" + " user_id," + " password,"
			+ " true," + "FROM" + " m_user" + " WHERE" + " user_id = ?";

	private static final String ROLE_SQL = "SELECT" + " user_id," + " role" + " FROM"
	+ " m_user" + " WHERE" + " user_id = ?";

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authz -> authz
				.mvcMatchers("/webjars/**", "/css/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/error").permitAll()
				//				.antMatchers("/bbbb").authenticated()//なぜか用意したエラーページが出る！
				.anyRequest().authenticated());

		http.csrf().disable();

		//		http.exceptionHandling()
		//		.authenticationEntryPoint(authenticationEntryPoint());
		//		.accessDeniedHandler(accessDeniedHandler());
		//		.accessDeniedPage("/error");

		http.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login")
				.usernameParameter("userId")
				.passwordParameter("password")
				.defaultSuccessUrl("/home", true);

		return http.build();
	}


	//参考URL：https://qiita.com/okaponta_/items/de1e640037b89b3ad6ca
	//ログイン処理時のユーザー情報をDBから取得する
	 @Bean
	    public UserDetailsManager users(DataSource dataSource) {
		 JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		 users.setUsersByUsernameQuery(USER_SQL);
	        users.setAuthoritiesByUsernameQuery(ROLE_SQL);
	        return users;
	    }

}
