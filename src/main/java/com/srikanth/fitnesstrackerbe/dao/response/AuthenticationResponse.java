package com.srikanth.fitnesstrackerbe.dao.response;

public record AuthenticationResponse(
        String username,
        String accessToken,
        String refreshToken) {

}