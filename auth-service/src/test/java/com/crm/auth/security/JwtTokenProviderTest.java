package com.crm.auth.security;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private static final String SECRET = "development-secret-key-with-at-least-32-characters";

    private final JwtTokenProvider provider = new JwtTokenProvider(SECRET, 900000, 604800000);

    @Test
    void shouldGenerateAndValidateAccessToken() {
        String token = provider.generateAccessToken("dinesh", 1L, List.of("USER"));

        assertTrue(provider.isValid(token));
        assertTrue(provider.isAccessToken(token));
        assertEquals("dinesh", provider.getUsername(token));
    }

    @Test
    void shouldGenerateRefreshToken() {
        String token = provider.generateRefreshToken("dinesh", 1L);

        assertTrue(provider.isValid(token));
        assertFalse(provider.isAccessToken(token));
        assertEquals("refresh", provider.parseClaims(token).get("type", String.class));
    }

    @Test
    void shouldRejectTamperedToken() {
        String token = provider.generateAccessToken("dinesh", 1L, List.of("USER"));

        assertFalse(provider.isValid(token + "tampered"));
    }
}
