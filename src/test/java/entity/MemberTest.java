package entity;

import com.surest.member.entity.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testSettersAndGetters() {
        Member member = new Member();

        UUID id = UUID.randomUUID();
        LocalDateTime dob = LocalDateTime.of(1990, 1, 1, 0, 0);
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();

        member.setId(id);
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setDateOfBirth(dob);
        member.setEmail("john@example.com");
        member.setCreatedAt(created);
        member.setUpdatedAt(updated);
        member.setVersion(1L);

        assertEquals(id, member.getId());
        assertEquals("John", member.getFirstName());
        assertEquals("Doe", member.getLastName());
        assertEquals(dob, member.getDateOfBirth());
        assertEquals("john@example.com", member.getEmail());
        assertEquals(created, member.getCreatedAt());
        assertEquals(updated, member.getUpdatedAt());
        assertEquals(1L, member.getVersion());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Member member = new Member(id, "A", "B", now, "mail@mail.com", now, now, 1L);

        assertEquals(id, member.getId());
        assertEquals("A", member.getFirstName());
        assertEquals("B", member.getLastName());
        assertEquals(now, member.getCreatedAt());
        assertEquals(now, member.getUpdatedAt());
        assertEquals(1L, member.getVersion());
    }

    @Test
    void testNotEquals() {
        Member m1 = new Member();
        Member m2 = new Member();
        m1.setId(UUID.randomUUID());
        m2.setId(UUID.randomUUID());

        assertNotEquals(m1, m2);
        assertNotEquals(m1, null);
        assertNotEquals(m1, "string");
    }

    @Test
    void testEqualsSameReference() {
        Member m = new Member();
        assertEquals(m, m);
    }

    @Test
    void testHashCodeNotThrow() {
        Member m = new Member();
        assertDoesNotThrow(m::hashCode);
    }

    @Test
    void testToStringNotThrow() {
        Member m = new Member();
        assertDoesNotThrow(m::toString);
    }
}
