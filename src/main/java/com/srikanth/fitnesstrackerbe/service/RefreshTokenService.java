package com.srikanth.fitnesstrackerbe.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.srikanth.fitnesstrackerbe.domain.RefreshToken;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.RefreshTokenRepository;
import com.srikanth.fitnesstrackerbe.dao.request.RefreshTokenRequest;

@Service
public class RefreshTokenService {

	@Value("${jwt.refreshTokenExpirationTimeInMillis}")
	private Long refreshTokenExpirationTimeInMillis;
	private RefreshTokenRepository refreshTokenRepository;
	private JwtService jwtService;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
		super();
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
	}
	 private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

	// To ensure id is not null, if user in repo, then make refresh token.
	// Save the new refresh token to our repo and then return the refresh token from
	// our repo. That is the workflow!
	public RefreshToken generateRefreshToken(User user) {
	    Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findById(user.getId());

	    RefreshToken refreshToken = null;
	    if (refreshTokenOpt.isPresent()) {
	        refreshToken = refreshTokenOpt.get();
	        refreshToken.setExpirationDate(getRefreshTokenExpirationDate());
	        refreshToken.setRefreshToken(generateRandomTokenValue());
	    } else {
	        refreshToken = new RefreshToken(user, generateRandomTokenValue(), getRefreshTokenExpirationDate());
	    }

	    refreshToken = refreshTokenRepository.save(refreshToken);
	    return refreshToken;
	}

	private String generateRandomTokenValue() {
		return UUID.randomUUID().toString();
	}

	private Date getRefreshTokenExpirationDate() {
		return new Date(System.currentTimeMillis() + refreshTokenExpirationTimeInMillis);
	}

//    public String createNewAccessToken(RefreshTokenRequest refreshTokenRequest) {
//        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken());
//        // TODO: write code to check that the RefreshToken hasn't expired
//        String accessToken = refreshTokenOpt.map(RefreshTokenService::isNonExpired)
//                .map(refreshToken -> jwtService.generateToken(new HashMap<>(), refreshToken.getUser()))
//                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
//        
//        return accessToken;
//        
//    }
	public String createNewAccessToken(RefreshTokenRequest refreshTokenRequest) {
	    System.out.println("Attempting to create a new access token with refresh token: " + refreshTokenRequest.refreshToken());
	    
	    Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken());

	    if (!refreshTokenOpt.isPresent()) {
	        System.out.println("Refresh token not found in the repository.");
	        throw new IllegalArgumentException("Refresh token not found");
	    }

	    String accessToken = refreshTokenOpt.map(refreshToken -> {
	        System.out.println("Found refresh token for user: " + refreshToken.getUser().getUsername());
	        try {
	            RefreshTokenService.isNonExpired(refreshToken);
	            System.out.println("Refresh token is valid and not expired.");
	            return jwtService.generateToken(new HashMap<>(), refreshToken.getUser());
	        } catch (IllegalArgumentException e) {
	            System.out.println("Refresh token has expired. Exception: " + e.getMessage());
	            throw e;
	        }
	    }).orElseThrow(() -> {
	        System.out.println("Failed to generate a new access token as the refresh token is missing or invalid.");
	        return new IllegalArgumentException("Refresh token not found");
	    });

	    System.out.println("Successfully generated a new access token.");
	    return accessToken;
	}

    
    /**
     * Calculate the remaining time in milliseconds until the refresh token expires.
     *
     * @param refreshToken The refresh token for which to calculate the remaining time.
     * @return The remaining time in milliseconds until the token expires.
     */
    public long getRemainingTimeForRefreshToken(RefreshToken refreshToken) {
        long expirationTimeMillis = refreshToken.getExpirationDate().getTime();
        long currentTimeMillis = System.currentTimeMillis();
        long remainingTimeMillis = expirationTimeMillis - currentTimeMillis;

        System.out.println("Refresh Token Validation Check:");
        System.out.println("  - Refresh Token Expiration Date: " + refreshToken.getExpirationDate());
        System.out.println("  - Current Time: " + new Date(currentTimeMillis));
        System.out.println("  - Milliseconds Remaining Until Token Expiration: " + remainingTimeMillis);
        System.out.println("  - Seconds Remaining Until Token Expiration: " + remainingTimeMillis / 1000);

        return remainingTimeMillis;
    }
    
    private static RefreshToken isNonExpired(RefreshToken refreshToken) {
        if (refreshToken.getExpirationDate().after(new Date())) {
            return refreshToken;
        } else {
            System.out.println("Refresh Token Expiration Check:");
            System.out.println("  - Token Expiration Date: " + refreshToken.getExpirationDate());
            System.out.println("  - Current Time: " + new Date());
            System.out.println("  - Token has expired for user: " + refreshToken.getUser().getUsername());

            throw new IllegalArgumentException("Refresh Token has expired for user: " + refreshToken.getUser().getUsername());
        }
    }


//    public long getRemainingTimeForRefreshToken(RefreshToken refreshToken) {
//        long expirationTimeMillis = refreshToken.getExpirationDate().getTime();
//        long currentTimeMillis = System.currentTimeMillis();
//        return expirationTimeMillis - currentTimeMillis;
//    }

    
//    private static RefreshToken isNonExpired(RefreshToken refreshToken) {
//        if (refreshToken.getExpirationDate().after(new Date())) {
//            return refreshToken;
//        } else {
//            throw new IllegalArgumentException("Refresh Token has expired for user: " + refreshToken.getUser().getUsername());
//        }
//    }


//    public void deleteRefreshToken(User loggedInUser) {
//        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findById(loggedInUser.getId());
//
//        if (refreshTokenOpt.isPresent()) {
//            RefreshToken refreshToken = refreshTokenOpt.get();
//            refreshTokenRepository.delete(refreshToken);
//        } 
//    }
    
    public void deleteRefreshTokenByUsername(String username) {
        refreshTokenRepository.findByUser_Username(username).ifPresent(refreshToken -> {
            refreshTokenRepository.delete(refreshToken);
            // Log the token deletion
            logger.info("Deleted refresh token for user: {}", username);
        });
    }



    
    
}