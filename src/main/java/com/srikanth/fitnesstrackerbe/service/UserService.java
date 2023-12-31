package com.srikanth.fitnesstrackerbe.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.srikanth.fitnesstrackerbe.domain.Authority;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.AuthorityRepository;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;

// implement spring security's user details service interface:
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;

    
	public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        // Check if the user was found
        if (!userOpt.isPresent()) {
            System.out.println("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = userOpt.get(); 
        
        System.out.println("User found: " + username + ", Authorities: " + user.getAuthorities());
        return user;
    }


    public Optional<User> findById (Integer userId) {
        return userRepository.findById(userId);
    }
    
    public User registerNewUser(String username, String password) {
        User user = new User(username, passwordEncoder.encode(password));

        // Find the authority by name
        Optional<Authority> authorityOpt = authorityRepository.findByName("ROLE_USER");
        
        if (!authorityOpt.isPresent()) {
            System.out.println("Authority not found: " + authorityOpt);
            throw new IllegalStateException("Authority not found: " + authorityOpt);
        }
        
        Authority authority = authorityOpt.get();

        // Set the selected authority
        user.setAuthorities(Collections.singletonList(authority));

        return userRepository.save(user);
    }
    



}