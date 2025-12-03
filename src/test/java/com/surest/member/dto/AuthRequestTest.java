package com.surest.member.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testEqualsAndHashCode() {
        AuthRequest a = new AuthRequest("user", "pass");
        AuthRequest b = new AuthRequest("user", "pass");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testToString() {
        AuthRequest req = new AuthRequest("user", "pass");
        String s = req.toString();
        assertTrue(s.contains("user"));
        assertTrue(s.contains("pass"));
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

