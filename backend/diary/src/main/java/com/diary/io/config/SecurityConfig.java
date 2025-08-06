package com.diary.io.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.diary.io.security.CustomUserDetailsService;
import com.diary.io.security.JwtAuthenticationFilter;
import com.diary.io.security.JwtProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
    }
    
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
	    return new JwtAuthenticationFilter(jwtProvider, customUserDetailsService);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		    .csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests(auth -> auth
		        .requestMatchers("/signup", "/login", "/api/auth/**").permitAll()
		        .requestMatchers("/api/diary/**").authenticated()
		        .anyRequest().permitAll()
		    )
		    .formLogin(login -> login.disable())
		    .httpBasic(basic -> basic.disable());
	
		http.cors(cors -> cors.configurationSource(request -> {
		    CorsConfiguration config = new CorsConfiguration();
		    config.setAllowedOrigins(List.of("http://localhost:5173"));
		    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		    config.setAllowedHeaders(List.of("*"));
		    config.setAllowCredentials(true);
		    return config;
		}));

		
		// JWT filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
   
}
