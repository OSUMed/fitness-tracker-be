package com.srikanth.fitnesstrackerbe.security;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.srikanth.fitnesstrackerbe.dao.request.RefreshTokenRequest;
import com.srikanth.fitnesstrackerbe.service.JwtService;
import com.srikanth.fitnesstrackerbe.service.RefreshTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import com.srikanth.fitnesstrackerbe.service.UserService;
import com.srikanth.fitnesstrackerbe.util.CookieUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.stereotype.Component;

@Component
// Once per request filter: because it passes by just once
// Where we get the tokens via req.headers
public class JwtAuthenticationFilter extends OncePerRequestFilter implements ApplicationContextAware {

	private JwtService jwtService;
	private ApplicationContext applicationContext;
	private RefreshTokenService refreshTokenService;

	// Requests:
	// Headers -> key/value pairs (Authorization -> Bearer xxx.yyy.zzz)
	// Body -> (if JSON) key/value pairs
//    String auth = request.getHeader("Authorization");

	public JwtAuthenticationFilter(JwtService jwtService, RefreshTokenService refreshTokenService) {
		super();
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
//		if (true) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			logger.info("Writing response TRUE e2 message...");
//	        response.getWriter().write("{\"error\":\"DAED TEST Unauthorized access. Please log in again.\"}");
//	        return;
//		}
	    String authHeader = request.getHeader("Authorization");
	    Cookie refreshTokenCookie = findCookie(request, "refreshToken");
	    if (StringUtils.hasText(authHeader)) {
	        String accessToken = authHeader.substring(7);
	        
	        if (accessToken != null) {
	            try {
	                String username = jwtService.getSubject(accessToken);
	                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	                if (StringUtils.hasText(username) && authentication == null) {
	                    if (jwtService.isTokenValid(accessToken, getUserDetails(username))) {
	                        setAuthenticationInContext(username);
	                    }
	                }
	            } catch (ExpiredJwtException e) {
	                // Handle access token expiration
	                handleAccessTokenExpiration(response, refreshTokenCookie);
	            } catch (Exception e) {
	                // General unauthorized error
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.setHeader("Authorization-Error", "true");
	                response.getWriter().write("{\"error\":\"Unauthorized access. Please log in again.\"}");
	                return;
	            }
	        }
	    } 
	    filterChain.doFilter(request, response);
	}

	private void handleAccessTokenExpiration(HttpServletResponse response, Cookie refreshTokenCookie) throws IOException {
	    try {
 	        String newToken = refreshAccessToken(refreshTokenCookie);
	        response.setHeader("Authorization-Refresh", newToken); // Set new token in response
	        setAuthenticationInContext(jwtService.getSubject(newToken));
	    } catch (IllegalArgumentException e2) {
	        if (e2.getMessage().startsWith("Refresh Token has expired for user: ")) {
	            String username = e2.getMessage().split("user: ")[1];
	 	        refreshTokenService.deleteRefreshTokenByUsername(username);
	 	        Cookie clearCookie = CookieUtils.clearServletCookie("refreshToken");
	 	        response.addCookie(clearCookie);

	 	        // Return a 401 response with the redirectToLogin flag
	 	        response.setHeader("Refresh-Token-Expired", "true");
	 	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	 	        response.setContentType("application/json");
	 	        logger.info("Writing response IllegalArgumentException e2 message...");
	 	        response.getWriter().write("{\"error\":\"Session expired. Please log in again.\", \"redirectToLogin\":true}");
	 	       return;
	        }
	    } catch (Exception e1) {
	        // Other exceptions during token refresh
	    	logger.info("Writing response Exception e1 message...");
	    	response.setHeader("Authorization-Error", "true");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getWriter().write("{\"error\":\"Error processing token. Please log in again.\"}");
	        return;
	    }
	}

	

	private UserDetails getUserDetails(String username) {
		UserDetailsService userDetailsService = applicationContext.getBean(UserDetailsService.class);
		return userDetailsService.loadUserByUsername(username);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;

	}

	private String refreshAccessToken(Cookie refreshTokenCookie) throws Exception {
		String newAccessToken = refreshTokenService
				.createNewAccessToken(new RefreshTokenRequest(refreshTokenCookie.getValue()));
		return newAccessToken;
	}

	private void setAuthenticationInContext(String username) {
		// Create final UsernamePasswordAuthenticationToken token. Then set it into security context
		// Notice we add user's roles here so spring security can check against it later
		UserDetails userDetails = getUserDetails(username);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		
		// Create and then set the authToken into the security context:
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authToken);
		SecurityContextHolder.setContext(securityContext);
	}

	private Cookie findCookie(HttpServletRequest request, String name) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

}