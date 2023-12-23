package com.srikanth.fitnesstrackerbe.dao.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken) {

}