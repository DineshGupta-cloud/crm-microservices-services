package com.crm.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-expiration-ms:900000}") long accessExpiration,
            @Value("${security.jwt.refresh-expiration-ms:604800000}") long refreshExpiration) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must contain at least 32 characters");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(String username, Long userId, List<String> roles) {
        return generateToken(username, userId, roles, accessExpiration, "access");
    }

    public String generateRefreshToken(String username, Long userId) {
        return generateToken(username, userId, List.of(), refreshExpiration, "refresh");
    }

    private String generateToken(String username, Long userId, List<String> roles, long expiration, String type) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .claim("type", type)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expiration)))
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return isValid(token) && "access".equals(parseClaims(token).get("type", String.class));
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }
}
