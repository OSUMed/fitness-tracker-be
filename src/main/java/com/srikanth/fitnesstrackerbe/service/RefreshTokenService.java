package com.srikanth.fitnesstrackerbe.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    
    private static RefreshToken isNonExpired (RefreshToken refreshToken) {
        if (refreshToken.getExpirationDate().after(new Date())) {
            return refreshToken;
        } else {
            throw new IllegalArgumentException("Refresh Token has expired");
        }
    }
    
    
}