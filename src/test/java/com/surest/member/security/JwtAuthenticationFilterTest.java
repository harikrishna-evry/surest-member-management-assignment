package com.surest.member.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {



    @Mock
    private JwtUtil jwtUtil;
    @Mock private CustomUserDetailsService userDetailsService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testBypassAuthEndpoints() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/auth/login");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // must pass through
        verify(jwtUtil, never()).validateToken(anyString());
    }

    @Test
    void testNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/members");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testInvalidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/members");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalid");

        when(jwtUtil.validateToken("invalid")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testValidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/members");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer valid");

        when(jwtUtil.validateToken("valid")).thenReturn(true);
        when(jwtUtil.getUsername("valid")).thenReturn("john");

        UserDetails mockUser =
                new User("john", "pwd", Collections.emptyList());

        when(userDetailsService.loadUserByUsername("john"))
                .thenReturn(mockUser);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        // Assert authentication is set
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(auth);
        assertEquals("john", auth.getName());
    }

    @Test
    void testHeaderDoesNotStartWithBearer() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/members");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Basic 123");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
