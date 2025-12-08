package com.surest.member.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {


    @Test
    void testAllArgsConstructorAndGetter() {
        AuthResponse r = new AuthResponse("abcd1234");
        assertEquals("abcd1234", r.getToken());
    }

    @Test
    void testNoArgsConstructorAndSetter() {
        AuthResponse r = new AuthResponse();
        r.setToken("xyz123");
        assertEquals("xyz123", r.getToken());
    }

    @Test
    void testEqualsAndHashcode() {
        AuthResponse r1 = new AuthResponse("t");
        AuthResponse r2 = new AuthResponse("t");

        assertEquals(r1, r1);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testNotEquals() {
        AuthResponse r1 = new AuthResponse("A");
        AuthResponse r2 = new AuthResponse("B");

        assertNotEquals(r1, r2);
        assertNotEquals(r1, null);
        assertNotEquals(r1, "string");
    }

    @Test
    void testHashcodeNotEquals() {
        AuthResponse r1 = new AuthResponse("A");
        AuthResponse r2 = new AuthResponse("B");

        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToString() {
        AuthResponse r = new AuthResponse("xyz");
        assertTrue(r.toString().contains("xyz"));
    }

    @Test
    void testNotEqualWithDifferentDTO() {
        AuthResponse r1 = new AuthResponse("A");
        CreateMemberRequest r2 = new CreateMemberRequest();
        assertNotEquals(r1, r2);
    }

    @Test
    void testNotEqualsWhenFieldNull() {
        AuthResponse a = new AuthResponse(null);
        AuthResponse b = new AuthResponse("token");
        assertNotEquals(a, b);
    }

}
