package com.project.RealEstateRental.dtos;

public class AuthenticationResponse {
    private String jwt;
    private String refreshToken;
    private long jwtExpiration;
    private long refreshTokenExpiration;

    public AuthenticationResponse(String jwt, String refreshToken, long jwtExpiration, long refreshTokenExpiration) {
        this.jwt = jwt;
        this.refreshToken = refreshToken;
        this.jwtExpiration = jwtExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String getJwt() {
        return jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}
