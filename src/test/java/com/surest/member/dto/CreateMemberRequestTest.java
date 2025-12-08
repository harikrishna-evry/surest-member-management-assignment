package com.surest.member.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class CreateMemberRequestTest {

    @Test
    void testGettersAndSetters() {
        CreateMemberRequest req = new CreateMemberRequest();

        LocalDateTime dob = LocalDateTime.of(1990, 1, 1, 0, 0);

        req.setFirstName("John");
        req.setLastName("Doe");
        req.setDateOfBirth(dob);
        req.setEmail("john@example.com");

        assertEquals("John", req.getFirstName());
        assertEquals("Doe", req.getLastName());
        assertEquals(dob, req.getDateOfBirth());
        assertEquals("john@example.com", req.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime dob = LocalDateTime.of(1992, 6, 10, 0, 0);
        CreateMemberRequest req =
                new CreateMemberRequest("Hari", "Krishna", dob, "hari@example.com");

        assertEquals("Hari", req.getFirstName());
        assertEquals("Krishna", req.getLastName());
        assertEquals(dob, req.getDateOfBirth());
        assertEquals("hari@example.com", req.getEmail());
    }

    @Test
    void testEqualsSameObject() {
        CreateMemberRequest req = new CreateMemberRequest();
        assertEquals(req, req);
    }

    @Test
    void testEqualsNotSame() {
        CreateMemberRequest r1 = new CreateMemberRequest("A", "B",
                LocalDateTime.now(), "a@mail.com");

        CreateMemberRequest r2 = new CreateMemberRequest("A", "B",
                LocalDateTime.now(), "b@mail.com");

        assertNotEquals(r1, r2);
    }


    @Test
    void testEqualsDifferentType() {
        CreateMemberRequest req = new CreateMemberRequest();
        assertNotEquals(req, "string");
    }

    @Test
    void testEqualsNull() {
        CreateMemberRequest req = new CreateMemberRequest();
        assertNotEquals(req, null);
    }

    @Test
    void testHashCode() {
        CreateMemberRequest r1 = new CreateMemberRequest();
        CreateMemberRequest r2 = new CreateMemberRequest();

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToStringNotThrow() {
        CreateMemberRequest req = new CreateMemberRequest();
        assertDoesNotThrow(req::toString);
    }
}

