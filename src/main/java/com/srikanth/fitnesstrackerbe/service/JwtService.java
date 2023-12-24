package com.srikanth.fitnesstrackerbe.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.RefreshToken;
import com.srikanth.fitnesstrackerbe.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
    
    /** 
     * What we need to be able to do with JWTs in this class
     * 0. Create a JWT signing key (Done)
     * 1. Create / generate the JWT
     * 2. Extract claims (ie get stuff from the payload)
     * 3. Verify that the JWT valid
     * 4. Sign the JWT
     */
    @Value("${jwt.signingKey}")
    private String jwtSigningKey;
    @Value("${jwt.expirationTimeInMillis}")
    private Long expirationTimeInMillis;
    
    public String generateToken(Map<String, Object> claims, UserDetails user) {
       // Add user roles to the claims
       claims.put("roles", user.getAuthorities());
        
       String jwt = Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setHeaderParam("typ", Header.JWT_TYPE)
            .setExpiration( new Date(System.currentTimeMillis() + expirationTimeInMillis) )
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)		// Does the Signing of JWT to make it valid
            .compact();													// Builds the JWT. A serialized compact thus compact()
        return jwt;
    }
    
    public Claims extractAllClaims (String token) {
        Claims body = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)		// parse info from jws
            .getBody();
        return body;
    }
    
    public String getSubject (String token) {
        String subject = extractClaim(token, Claims::getSubject);	// extractClaims is an interface that takes function -> getSubj R
        return subject;
    }
    
    public Boolean isTokenValid (String token, UserDetails user) {
        String subject = getSubject(token);
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return user.getUsername().equalsIgnoreCase(subject) && new Date().before(expirationDate);
    }
    
    private <R> R extractClaim (String token, Function<Claims, R> claimsExtract) {
        Claims allClaims = extractAllClaims(token);
        return claimsExtract.apply(allClaims);		// Apply comes with that interface
    }

    // Secret Key Formation: String -> Bites -> Secret Key
    private Key getSigningKey() {
        byte[] jwtSigningKeyAsBytes = Decoders.BASE64.decode(jwtSigningKey);
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSigningKeyAsBytes);
        return secretKey;
    }


    public void setJwtSigningKey(String jwtSigningKey) {
        if (this.jwtSigningKey == null)
            this.jwtSigningKey = jwtSigningKey;
        
    }

    public void setExpirationTimeInMillis(Long expirationTimeInMillis) {
        if (this.expirationTimeInMillis == null)
            this.expirationTimeInMillis = expirationTimeInMillis;
    }

	
    
}