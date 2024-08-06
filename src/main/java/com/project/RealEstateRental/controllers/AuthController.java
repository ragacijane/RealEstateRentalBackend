package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.requests.AuthenticationRequest;
import com.project.RealEstateRental.requests.AuthenticationResponse;
import com.project.RealEstateRental.services.JwtService;
import com.project.RealEstateRental.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtService.generateToken(userDetails.getUsername());
        final String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());
        final Date jwtExpirationDate = jwtService.extractExpiration(jwt);
        final Date refreshTokenExpirationDate = jwtService.extractExpiration(refreshToken);

        return new AuthenticationResponse(jwt, refreshToken, jwtExpirationDate.getTime(), refreshTokenExpirationDate.getTime());
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshAuthenticationToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        if (jwtService.isTokenExpired(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        }
        String username = jwtService.extractUsername(token);
        String newToken = jwtService.generateToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);
        final Date jwtExpirationDate = jwtService.extractExpiration(newToken);
        final Date refreshTokenExpirationDate = jwtService.extractExpiration(newRefreshToken);

        return new AuthenticationResponse(newToken, newRefreshToken, jwtExpirationDate.getTime(), refreshTokenExpirationDate.getTime());
    }
}
