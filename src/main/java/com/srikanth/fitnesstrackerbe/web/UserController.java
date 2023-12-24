package com.srikanth.fitnesstrackerbe.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;

import com.srikanth.fitnesstrackerbe.domain.RefreshToken;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.RefreshTokenRepository;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.dao.request.RefreshTokenRequest;
import com.srikanth.fitnesstrackerbe.dao.response.AuthenticationResponse;
import com.srikanth.fitnesstrackerbe.dao.response.LogoutResponse;
import com.srikanth.fitnesstrackerbe.dao.response.RefreshTokenResponse;
import com.srikanth.fitnesstrackerbe.dao.response.RegisterationResponse;
import com.srikanth.fitnesstrackerbe.service.JwtService;
import com.srikanth.fitnesstrackerbe.service.RefreshTokenService;
import com.srikanth.fitnesstrackerbe.service.UserService;
import com.srikanth.fitnesstrackerbe.util.CookieUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import com.srikanth.fitnesstrackerbe.util.CookieUtils;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	private UserService userService;
	private RefreshTokenRepository refreshTokenRepository;
	private RefreshTokenService refreshTokenService;

	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			UserService userService, RefreshTokenService refreshTokenService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}

	// For Non-JWT version:
//    @PostMapping("")
//    public ResponseEntity<User> signUpUser (@RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        
//        return ResponseEntity.ok(savedUser);
//    }
    @PostMapping("")
    public ResponseEntity<RegisterationResponse> signUpUser (@RequestBody User user) {
    	User savedUser = userService.registerNewUser(user.getUsername(), user.getPassword());
    	RegisterationResponse regResponse = new RegisterationResponse(savedUser.getUsername());
 
		return ResponseEntity
				.ok()
				.body(regResponse);
	}

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody User user) {
        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateToken(new HashMap<>(), loggedInUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loggedInUser);

        // Calculate the duration for the cookie based on the refresh token's expiration
        long remainingAge = refreshTokenService.getRemainingTimeForRefreshToken(refreshToken);
        
        // Create the refresh token cookie
        ResponseCookie refreshTokenCookie = CookieUtils.createRefreshTokenCookie(
            refreshToken.getRefreshToken(), 
            remainingAge
        );

        // Create the authentication response object
        AuthenticationResponse authResponse = new AuthenticationResponse(
            loggedInUser.getUsername(), 
            accessToken, 
            refreshToken.getRefreshToken()
        );

        // Build the response and add the refresh token cookie and access token to the headers
        return ResponseEntity.ok()
                             .header("Set-Cookie", refreshTokenCookie.toString())
                             .header("Authorization", "Bearer " + accessToken)
                             .body(authResponse);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logoutUser(@RequestBody User user, HttpServletResponse response) {
        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());

        // Invalidate refresh token in the database
        refreshTokenService.deleteRefreshToken(loggedInUser);

        // Clear refresh token cookie using CookieUtils
        ResponseCookie refreshTokenCookie = CookieUtils.clearCookie("refreshToken");
        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok(new LogoutResponse("Successfully logged out"));
    }

	@PostMapping("/refreshtoken")
	public ResponseEntity<RefreshTokenResponse> getNewAccessToken(
			@RequestBody RefreshTokenRequest refreshTokenRequest) {
		String accessToken = refreshTokenService.createNewAccessToken(refreshTokenRequest);
		return ResponseEntity.ok(new RefreshTokenResponse(accessToken, refreshTokenRequest.refreshToken()));
	}
	
	@GetMapping("/getusers")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
}