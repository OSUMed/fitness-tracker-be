package com.srikanth.fitnesstrackerbe.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;

import com.srikanth.fitnesstrackerbe.domain.RefreshToken;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.AuthorityRepository;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.service.JwtService;
import com.srikanth.fitnesstrackerbe.service.RefreshTokenService;
import com.srikanth.fitnesstrackerbe.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		// On default use
		return new BCryptPasswordEncoder();
	}

	// User Details Service: Load user by user name:
	@Bean
	public UserDetailsService userDetailsService() {
		return userService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    String[] pathsPermitAll = { "/api/v1/users", "/allusers", "/api/v1/users/**", "/h2-console/**", "/free", "/signup" };
	    http.csrf(AbstractHttpConfigurer::disable)
	        .authorizeHttpRequests(authz -> {
	            for (String path : pathsPermitAll) {
	                authz.requestMatchers(new AntPathRequestMatcher(path)).permitAll();
	            }
	            authz.requestMatchers(new AntPathRequestMatcher("/user/welcome")).authenticated()
	                 .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority("ROLE_ADMIN")
	                 .requestMatchers(new AntPathRequestMatcher("/user/**")).hasAuthority("ROLE_USER")
	                 .anyRequest().authenticated();
	        })
	        .headers(frameOptions -> frameOptions.disable())
	        .authenticationProvider(authenticationProvider())
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	

	    return http.build();
	}



	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		return daoAuthenticationProvider;
	}

}