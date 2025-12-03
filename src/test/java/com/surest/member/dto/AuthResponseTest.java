package com.surest.member.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {


    @Test
    void testNoArgsConstructorAndSetters() {
        AuthResponse response = new AuthResponse();
        response.setToken("abcd1234");

        assertEquals("abcd1234", response.getToken());
    }

    @Test
    void testAllArgsConstructor() {
        AuthResponse response = new AuthResponse("jwt-token-123");
        assertEquals("jwt-token-123", response.getToken());
    }

    @Test
    void testToString() {
        AuthResponse response = new AuthResponse("jwt-token-xyz");

        String str = response.toString();

        assertNotNull(str);
        assertTrue(str.contains("jwt-token-xyz"));
    }

    @Test
    void testNotEquals() {
        CreateMemberRequest r1 = new CreateMemberRequest("A", "B", null, "a@test.com");
        CreateMemberRequest r2 = new CreateMemberRequest("X", "Y", null, "x@test.com");

        assertNotEquals(r1, r2);       // different values
        assertNotEquals(r1, null);     // compare with null
        assertNotEquals(r1, "string"); // compare with other type
    }

    @Test
    void testHashcodeNotEquals() {
        CreateMemberRequest r1 = new CreateMemberRequest("A", "B", null, "a@test.com");
        CreateMemberRequest r2 = new CreateMemberRequest("X", "Y", null, "x@test.com");

        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

}
