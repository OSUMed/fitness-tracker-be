package com.srikanth.fitnesstrackerbe.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.service.JwtService;
import com.srikanth.fitnesstrackerbe.service.RefreshTokenService;
import com.srikanth.fitnesstrackerbe.service.UserService;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.repository.RefreshTokenRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
	private UserService userService;
	UserRepository userRepository;
	
	public AdminController(UserRepository userRepository, UserService userService) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@GetMapping("/hello")
	public ResponseEntity<String> userEndpoints() {
		System.out.println("Hello you reached admin endpoint");
	    return ResponseEntity.ok("Admin Endpoint reached");
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
	}
	@GetMapping("/private")
    public ResponseEntity<Map<String, String>> getPrivate() {
        Map<String, String> response = new HashMap<>();
        response.put("username", "George");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/dashboard")
    public List<User> getDashboard () {
    	List<User> users = userService.findAll();
    	return users;
    }
    
    @PostMapping("/makeAdmin")
    public ResponseEntity<String> elevateToAdmin (@RequestParam Integer userId) {
    	Optional<User> findUser = userService.findById(userId);
    	userService.elevateUserToAdmin(userId);
    	return ResponseEntity.ok("User elevated to admin");
    }
    
    @PostMapping("/demoteAdmin")
    public ResponseEntity<String> demoteToGuest (@RequestParam Integer userId) {
    	Optional<User> findUser = userService.findById(userId);
    	userService.demoteToUser(userId);
    	return ResponseEntity.ok("User elevated to admin");
    }

}