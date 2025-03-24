package com.saintsau.slam2.gnotes30.authentification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); // This disables any password encoding
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf
	            .ignoringRequestMatchers(request -> "GET".equals(request.getMethod())) // Ignore CSRF for all GET requests
	        )
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/", "/home").permitAll()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .requestMatchers("/enseignant/**").hasRole("ENSEIGNANT")
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .permitAll()
	        )
	        .logout(logout -> logout.permitAll())
	        .sessionManagement(session -> session
	            .maximumSessions(1)
	            .expiredUrl("/login?expired=true")
	        );

	    return http.build();
	}


	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		// Using JdbcUserDetailsManager to fetch users from a database
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource);

		// Define the SQL queries to load user and authorities (roles)
		userDetailsService.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
		userDetailsService
				.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");

		return userDetailsService;
	}
}
