package entity;

import com.surest.member.entity.UserRole;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserRoleTest {

    @Test
    void testGettersAndSetters() {
        UserRole role = new UserRole();
        UUID id = UUID.randomUUID();
        role.setId(id);
        role.setName("ROLE_USER");
        role.setVersion(1L);

        assertEquals(id, role.getId());
        assertEquals("ROLE_USER", role.getName());
        assertEquals(1L, role.getVersion());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UserRole role = new UserRole(id, "ROLE_ADMIN", 0L);

        assertEquals(id, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
        assertEquals(0L, role.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        UserRole r1 = new UserRole(id, "ROLE_TEST", 0L);
        UserRole r2 = new UserRole(id, "ROLE_TEST", 0L);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToStringNotThrow() {
        UserRole role = new UserRole();
        assertDoesNotThrow(role::toString);
    }

    @Test
    void testEqualsNotSame() {
        UserRole r1 = new UserRole();
        UserRole r2 = new UserRole();
        r1.setId(UUID.randomUUID());
        r2.setId(UUID.randomUUID());
        assertNotEquals(r1, r2);
    }
}
