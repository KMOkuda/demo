package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//参考になるURL https://qiita.com/opengl-8080/items/032ed0fa27a239bdc1cc

@EnableWebSecurity
@Configuration
@PropertySource("classpath:/application.properties")
public class SecurityConfig {

	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;

	private static final String USER_SQL = "SELECT" + " user_id," + " password," + " true" + " FROM" + " m_user"
			+ " WHERE" + " user_id = ?";

	private static final String ROLE_SQL = "SELECT" + " user_id," + " role" + " FROM" + " m_user" + " WHERE"
			+ " user_id = ?";

	/**
	 * @param http
	 * @return
	 * @throws Exception
	 *
	 *                   アクセス権限について規定
	 */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((auththorizeRequests) -> auththorizeRequests
				.requestMatchers("/webjars/**", "/css/**").permitAll().requestMatchers("/login").permitAll()
				.requestMatchers("/signup").permitAll().requestMatchers("/error").permitAll().requestMatchers("/admin")
				.hasAuthority("ROLE_ADMIN").anyRequest().authenticated())
		/**
				.oauth2Login((oauth2Customize) -> oauth2Customize.loginProcessingUrl("/login")
						.loginPage("/auth2/authorization/google").successHandler(new AuthenticationSuccessHandler() {
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request,
									HttpServletResponse response, Authentication authentication)
									throws IOException, ServletException {
								request.authenticate(response);
							}
						}).failureHandler(new AuthenticationFailureHandler() {

							@Override
							public void onAuthenticationFailure(HttpServletRequest request,
									HttpServletResponse response, AuthenticationException exception)
									throws IOException, ServletException {

							}
						}))**/;

		http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/login")
				.usernameParameter("userId").passwordParameter("password").defaultSuccessUrl("/home", true);

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutUrl("/logout")
				.logoutSuccessUrl("/login");

		return http.build();
	}

	// 参考URL：https://qiita.com/okaponta_/items/de1e640037b89b3ad6ca
	// ログイン処理時のユーザー情報をDBから取得する
	@Bean
	public UserDetailsManager users(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		users.setUsersByUsernameQuery(USER_SQL);
		users.setAuthoritiesByUsernameQuery(ROLE_SQL);

		return users;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	public OAuth2AuthorizedClientManager auth2AuthorizedClientManager(
		ClientRegistrationRepository clientRegistrationRepository,
		OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
			) {
		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.authorizationCode()
				.refreshToken()
				.build();
		DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientRepository);
		
		
	}
	
	
	
	
	/**
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 *            throws Exception { auth.inMemoryAuthentication()
	 *            .withUser("user").password("{noop}password").roles("USER"); }
	 **/
	
	
	/**
	
	@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId("68918077399-2jgt3uc2kfd8rb48j16dojd05uiptrjr.apps.googleusercontent.com")
                .clientSecret("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")
                .build());
    }
	
    
	/**
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }
	
	private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
            .clientId("google-client-id")
            .clientSecret("google-client-secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .scope("openid", "profile", "email", "address", "phone")
            .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
            .tokenUri("https://www.googleapis.com/oauth2/v4/token")
            .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
            .clientName("Google")
            .build();
    }
    
    **/
}
