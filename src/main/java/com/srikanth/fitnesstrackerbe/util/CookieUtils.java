package com.srikanth.fitnesstrackerbe.util;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;

public class CookieUtils {

    // Existing method for creating a refresh token cookie
    public static ResponseCookie createRefreshTokenCookie(String value, long expiresIn) {
        return ResponseCookie.from("refreshToken", value)
                             .path("/")
                             .httpOnly(true)
                             .maxAge(expiresIn)
                             .secure(true) // HTTPS
                             .build();
    }

    // New method for clearing a cookie
    public static ResponseCookie clearCookie(String name) {
        return ResponseCookie.from(name, "")
                             .path("/")
                             .httpOnly(true)
                             .maxAge(0) // Expire the cookie immediately
                             .secure(true) // HTTPS
                             .build();
    }
    public static Cookie clearServletCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Expire the cookie immediately
        cookie.setSecure(true); // HTTPS
        return cookie;
    }
}
