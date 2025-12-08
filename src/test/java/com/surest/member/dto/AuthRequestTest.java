package com.surest.member.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        AuthRequest a = new AuthRequest("user", "pass");

        assertEquals("user", a.getUsername());
        assertEquals("pass", a.getPassword());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        AuthRequest a = new AuthRequest();
        a.setUsername("hari");
        a.setPassword("123");

        assertEquals("hari", a.getUsername());
        assertEquals("123", a.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthRequest a = new AuthRequest("user", "pass");
        AuthRequest b = new AuthRequest("user", "pass");

        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testNotEquals() {
        AuthRequest a = new AuthRequest("user", "pass");
        AuthRequest b = new AuthRequest("abc", "xyz");

        assertNotEquals(a, b);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
    }

    @Test
    void testHashcodeNotEquals() {
        AuthRequest a = new AuthRequest("user", "pass");
        AuthRequest b = new AuthRequest("abc", "xyz");

        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testToString() {
        AuthRequest a = new AuthRequest("hari", "test");
        assertTrue(a.toString().contains("hari"));
    }

    @Test
    void testNotEqualsWhenFieldNull() {
        AuthRequest a = new AuthRequest(null, "123");
        AuthRequest b = new AuthRequest("abc", "123");
        assertNotEquals(a, b);
    }

}
