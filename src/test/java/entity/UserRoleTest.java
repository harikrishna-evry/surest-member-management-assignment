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
        String roleName = "ROLE_USER";

        role.setId(id);
        role.setName(roleName);

        assertEquals(id, role.getId());
        assertEquals(roleName, role.getName());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        String roleName = "ROLE_ADMIN";

        UserRole role = new UserRole(id, roleName);

        assertEquals(id, role.getId());
        assertEquals(roleName, role.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();

        UserRole role1 = new UserRole(id, "ROLE_TEST");
        UserRole role2 = new UserRole(id, "ROLE_TEST");

        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());
    }
}
