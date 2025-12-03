package com.surest.member.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CreateMemberRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        CreateMemberRequest request = new CreateMemberRequest();

        LocalDateTime dob = LocalDateTime.of(1998, 5, 3, 0, 0);

        request.setFirstName("Hari");
        request.setLastName("Krishna");
        request.setDateOfBirth(dob);
        request.setEmail("hari@test.com");

        assertEquals("Hari", request.getFirstName());
        assertEquals("Krishna", request.getLastName());
        assertEquals(dob, request.getDateOfBirth());
        assertEquals("hari@test.com", request.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime dob = LocalDateTime.of(1998, 5, 3, 0, 0);

        CreateMemberRequest request = new CreateMemberRequest(
                "Hari",
                "Krishna",
                dob,
                "hari@test.com"
        );

        assertEquals("Hari", request.getFirstName());
        assertEquals("Krishna", request.getLastName());
        assertEquals(dob, request.getDateOfBirth());
        assertEquals("hari@test.com", request.getEmail());
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
