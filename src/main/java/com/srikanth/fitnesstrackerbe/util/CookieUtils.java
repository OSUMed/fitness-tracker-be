package com.srikanth.fitnesstrackerbe.util;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;

public class CookieUtils {

    public static String createRefreshTokenCookie(String value, long expiresIn) {
        // Manually construct the Set-Cookie header value
        return String.format(
            "refreshToken=%s; Path=/; HttpOnly; Max-Age=%d; Secure; SameSite=None",
            value,
            expiresIn
        );
    }

    // New method for clearing a cookie
    public static ResponseCookie clearCookie(String name) {
        return ResponseCookie.from(name, "")
                             .path("/")
                             .httpOnly(true)
                             .maxAge(0) 
                             .secure(false) // HTTPS
                             .build();
    }
    public static Cookie clearServletCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); 
        cookie.setSecure(false); // HTTPS
        return cookie;
    }
}
