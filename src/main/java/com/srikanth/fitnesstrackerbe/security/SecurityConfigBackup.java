package com.srikanth.fitnesstrackerbe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfigBackup {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .addFilterBefore(new CorsFilter(corsConfigurationSource), CsrfFilter.class) // Add CorsFilter
            .csrf(csrf -> csrf.disable()) // Optionally disable CSRF
            .authorizeHttpRequests((authz) -> authz
        		.requestMatchers("/private/**").authenticated()
                .anyRequest().permitAll() // Require authentication for all requests
            )
            .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Authentication with default settings

        return http.build();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
            .withUser("username")
            .password(encoder.encode("123"))
            .roles("USER");
    }
}
