package com.surest.member.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {



    private JwtUtil jwtUtil;

    // A 32+ byte key required for HS256
    private static final String SECRET = "this_is_a_very_secure_secret_key_1234567890";
    private static final long EXPIRATION_MS = 1000 * 60; // 1 minute

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil(SECRET, EXPIRATION_MS);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("hari", List.of("ROLE_USER"));
        assertNotNull(token);
    }

    @Test
    void testValidateToken_validToken() {
        String token = jwtUtil.generateToken("hari", List.of("ROLE_USER"));

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testGetUsername() {
        String token = jwtUtil.generateToken("hari", List.of("ROLE_USER"));

        String username = jwtUtil.getUsername(token);

        assertEquals("hari", username);
    }

    @Test
    void testValidateToken_invalidSignature() {
        String token = jwtUtil.generateToken("hari", List.of("ROLE_USER"));

        String tampered = token.substring(0, token.length() - 5) + "abcde";

        assertFalse(jwtUtil.validateToken(tampered));
    }

    @Test
    void testValidateToken_malformedToken() {
        assertFalse(jwtUtil.validateToken("this.is.not.valid.jwt"));
    }

    @Test
    void testValidateToken_expiredToken() throws InterruptedException {

        JwtUtil shortLived = new JwtUtil(SECRET, 1);

        String token = shortLived.generateToken("hari", List.of("ROLE_USER"));

        Thread.sleep(5);

        assertFalse(shortLived.validateToken(token));
    }


}
