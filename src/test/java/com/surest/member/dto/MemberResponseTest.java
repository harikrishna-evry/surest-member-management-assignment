package com.surest.member.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        LocalDateTime dob = LocalDateTime.of(1990, 1, 1, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        MemberResponse resp = new MemberResponse(
                id, "Hari", "A", dob, "hari@test.com", now, now
        );

        assertEquals(id, resp.getId());
        assertEquals("Hari", resp.getFirstName());
        assertEquals("A", resp.getLastName());
        assertEquals(dob, resp.getDateOfBirth());
        assertEquals("hari@test.com", resp.getEmail());
        assertEquals(now, resp.getCreatedAt());
        assertEquals(now, resp.getUpdatedAt());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        UUID id = UUID.randomUUID();
        LocalDateTime dob = LocalDateTime.of(1980, 1, 1, 0, 0);
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = created.plusHours(1);

        MemberResponse resp = new MemberResponse();
        resp.setId(id);
        resp.setFirstName("John");
        resp.setLastName("Doe");
        resp.setDateOfBirth(dob);
        resp.setEmail("john@example.com");
        resp.setCreatedAt(created);
        resp.setUpdatedAt(updated);

        assertEquals(id, resp.getId());
        assertEquals("John", resp.getFirstName());
        assertEquals("Doe", resp.getLastName());
        assertEquals(dob, resp.getDateOfBirth());
        assertEquals("john@example.com", resp.getEmail());
        assertEquals(created, resp.getCreatedAt());
        assertEquals(updated, resp.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCodeSameValues() {
        UUID id = UUID.randomUUID();
        LocalDateTime dob = LocalDateTime.of(1995, 5, 5, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        MemberResponse r1 = new MemberResponse(id, "Jane", "Doe", dob, "jane@test.com", now, now);
        MemberResponse r2 = new MemberResponse(id, "Jane", "Doe", dob, "jane@test.com", now, now);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testEqualsSameReference() {
        MemberResponse r = new MemberResponse();
        assertEquals(r, r);
    }

    @Test
    void testEqualsWithNull() {
        MemberResponse r1 = new MemberResponse();
        assertNotEquals(r1, null);
    }

    @Test
    void testEqualsWithDifferentType() {
        MemberResponse r1 = new MemberResponse();
        assertNotEquals(r1, "string");
    }

    @Test
    void testNotEqualDifferentFieldValues() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        MemberResponse r1 = new MemberResponse(id1, "A", "B", now, "a@mail.com", now, now);
        MemberResponse r2 = new MemberResponse(id2, "A", "B", now, "a@mail.com", now, now);

        assertNotEquals(r1, r2);
    }

    @Test
    void testEqualsWhenSomeFieldsNull() {
        MemberResponse r1 = new MemberResponse();
        MemberResponse r2 = new MemberResponse();

        r1.setFirstName(null);
        r2.setFirstName("X");

        assertNotEquals(r1, r2);
    }

    @Test
    void testHashCodeWhenFieldsNull() {
        MemberResponse r1 = new MemberResponse();
        MemberResponse r2 = new MemberResponse();

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToStringDoesNotThrow() {
        MemberResponse resp = new MemberResponse();
        assertDoesNotThrow(resp::toString);
    }
}
