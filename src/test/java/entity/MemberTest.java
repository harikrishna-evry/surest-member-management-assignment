package entity;

import com.surest.member.entity.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {


    @Test
    void testSettersAndGetters() {

        Member member = new Member();

        UUID id = UUID.randomUUID();
        LocalDateTime dob = LocalDateTime.of(1998, 5, 3, 0, 0);
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();

        member.setId(id);
        member.setFirstName("Hari");
        member.setLastName("Krishna");
        member.setDateOfBirth(dob);
        member.setEmail("hari@test.com");
        member.setCreatedAt(created);
        member.setUpdatedAt(updated);

        assertEquals(id, member.getId());
        assertEquals("Hari", member.getFirstName());
        assertEquals("Krishna", member.getLastName());
        assertEquals(dob, member.getDateOfBirth());
        assertEquals("hari@test.com", member.getEmail());
        assertEquals(created, member.getCreatedAt());
        assertEquals(updated, member.getUpdatedAt());
    }
}
