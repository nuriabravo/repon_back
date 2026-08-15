package com.nuriabravo.repon.application.service.impl;

import com.nuriabravo.repon.application.dto.output.LoginResponse;
import com.nuriabravo.repon.application.service.UserAuthService;
import com.nuriabravo.repon.domain.exception.InvalidCredentialsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.auth.username}")
    private String configuredUsername;

    @Value("${app.auth.password-hash}")
    private String configuredPasswordHash;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-days:30}")
    private long expirationDays;

    public UserAuthServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(String username, String rawPassword) {
        validateCredentials(username, rawPassword);
        return generateToken();
    }

    private void validateCredentials(String username, String rawPassword) {
        boolean usernameOk = configuredUsername.equals(username);
        boolean passwordOk = passwordEncoder.matches(rawPassword, configuredPasswordHash);
        if (!usernameOk || !passwordOk) {
            throw new InvalidCredentialsException();
        }
    }

    private LoginResponse generateToken() {
        Instant expiration = Instant.now().plus(expirationDays, ChronoUnit.DAYS);
        String token = Jwts.builder()
                .subject(configuredUsername)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiration))
                .signWith(signingKey())
                .compact();
        return new LoginResponse(token, expiration);
    }

    @Override
    public boolean isTokenValid(String token) {
        if (token == null) return false;
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}