package com.todos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
				.csrf(AbstractHttpConfigurer::disable) // Disable CSRF (cross site request forgery)
				.authorizeHttpRequests(authorize ->
						authorize
//								.requestMatchers("/admin/**").hasRole("ADMIN") // Защита для админских страниц
								.requestMatchers("/**").permitAll()
//								.requestMatchers("/public/**").permitAll()     // Открытые маршруты для всех
//								.anyRequest().authenticated()                  // Все остальные маршруты требуют аутентификации
				)
				.formLogin(withDefaults());  // Включить форму для аутентификации
		
		return http.build();
	}

}