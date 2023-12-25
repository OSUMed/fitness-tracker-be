package com.srikanth.fitnesstrackerbe.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	 private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


	@Bean
	public PasswordEncoder passwordEncoder() {
		// On default use
		return new BCryptPasswordEncoder();
	}



	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    String[] pathsPermitAll = { "/api/v1/users", "/logout", "/actuator/**", "/account", "/allusers", "/refreshtoken", "/tryme", "/api/v1/users/**", "/h2-console/**", "/free", "/register", "/login" };
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
	        .sessionManagement(sessionManagement -> 
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	

	    return http.build();
	}



	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

}
//
//package com.srikanth.fitnesstrackerbe.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CsrfFilter;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.security.config.Customizer;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfigBackup {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
//        http
//            .addFilterBefore(new CorsFilter(corsConfigurationSource), CsrfFilter.class) // Add CorsFilter
//            .csrf(csrf -> csrf.disable()) // Optionally disable CSRF
//            .authorizeHttpRequests((authz) -> authz
//        		.requestMatchers("/private/**").authenticated()
//                .anyRequest().permitAll() // Require authentication for all requests
//            )
//            .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Authentication with default settings
//
//        return http.build();
//    }
//
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        auth.inMemoryAuthentication()
//            .withUser("username")
//            .password(encoder.encode("123"))
//            .roles("USER");
//    }
//}
//
