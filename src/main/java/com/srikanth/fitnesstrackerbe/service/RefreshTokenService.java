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

    public String createNewAccessToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken());
        // TODO: write code to check that the RefreshToken hasn't expired
        String accessToken = refreshTokenOpt.map(RefreshTokenService::isNonExpired)
                .map(refreshToken -> jwtService.generateToken(new HashMap<>(), refreshToken.getUser()))
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        
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
        return expirationTimeMillis - currentTimeMillis;
    }

    
    private static RefreshToken isNonExpired (RefreshToken refreshToken) {
        if (refreshToken.getExpirationDate().after(new Date())) {
            return refreshToken;
        } else {
            throw new IllegalArgumentException("Refresh Token has expired");
        }
    }

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