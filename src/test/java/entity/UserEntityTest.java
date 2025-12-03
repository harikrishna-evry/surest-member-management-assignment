package entity;

import com.surest.member.entity.UserEntity;
import com.surest.member.entity.UserRole;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {


    @Test
    void testUserEntityGettersAndSetters() {
        UserEntity user = new UserEntity();

        UUID id = UUID.randomUUID();
        String username = "hari";
        String passwordHash = "hashed_password";


        UserRole role = new UserRole();
        role.setId(UUID.randomUUID());
        role.setName("ROLE_USER");


        user.setId(id);
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        user.setRole(role);

        // Verify values
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(role, user.getRole());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UserRole role = new UserRole(UUID.randomUUID(), "ROLE_ADMIN");

        UserEntity user = new UserEntity(id, "admin", "pass123", role);

        assertEquals(id, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("pass123", user.getPasswordHash());
        assertEquals(role, user.getRole());
    }
}
