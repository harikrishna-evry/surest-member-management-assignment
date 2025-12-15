package entity;

import com.surest.member.entity.UserEntity;
import com.surest.member.entity.UserRole;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    @Test
    void testGettersAndSetters() {
        UserEntity user = new UserEntity();

        UUID id = UUID.randomUUID();
        UserRole role = new UserRole(UUID.randomUUID(), "ROLE_USER", 0L);

        user.setId(id);
        user.setUsername("hari");
        user.setPasswordHash("123");
        user.setRole(role);
        user.setVersion(1L);

        assertEquals(id, user.getId());
        assertEquals("hari", user.getUsername());
        assertEquals("123", user.getPasswordHash());
        assertEquals(role, user.getRole());
        assertEquals(1L, user.getVersion());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UserRole role = new UserRole(UUID.randomUUID(), "ROLE_ADMIN", 0L);

        UserEntity user = new UserEntity(id, "admin", "pass", role, 2L);

        assertEquals(id, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("pass", user.getPasswordHash());
        assertEquals(role, user.getRole());
        assertEquals(2L, user.getVersion());
    }

    @Test
    void testToStringNotThrow() {
        UserEntity user = new UserEntity();
        assertDoesNotThrow(user::toString);
    }

    @Test
    void testHashCodeNotThrow() {
        UserEntity user = new UserEntity();
        assertDoesNotThrow(user::hashCode);
    }

    @Test
    void testEqualsNotSame() {
        UserEntity u1 = new UserEntity();
        UserEntity u2 = new UserEntity();
        u1.setId(UUID.randomUUID());
        u2.setId(UUID.randomUUID());
        assertNotEquals(u1, u2);
    }
}
