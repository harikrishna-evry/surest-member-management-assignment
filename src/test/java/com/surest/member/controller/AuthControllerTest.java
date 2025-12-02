package com.surest.member.controller;

import com.surest.member.dto.AuthRequest;
import com.surest.member.dto.AuthResponse;
import com.surest.member.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class AuthControllerTest {

    private AuthenticationManager authManager;
    private JwtUtil jwtUtil;
    private AuthController controller;

    @BeforeEach
    void setup() {
        authManager = mock(AuthenticationManager.class);
        jwtUtil = mock(JwtUtil.class);
        controller = new AuthController(authManager, jwtUtil);
    }

    @Test
    void testLoginSuccess() {

        AuthRequest req = new AuthRequest();
        req.setUsername("mohan");
        req.setPassword("123");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("mohan");
        when(mockAuth.getAuthorities()).thenReturn(
                (List) List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        when(jwtUtil.generateToken("mohan", List.of("ROLE_USER")))
                .thenReturn("fake-jwt-token");

        AuthResponse response = controller.login(req).getBody();

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
    }
}
