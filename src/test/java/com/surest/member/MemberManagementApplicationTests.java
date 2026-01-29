package com.surest.member;

import com.surest.member.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles("test")
@SpringBootTest
class MemberManagementApplicationTests extends IntegrationTestBase {

    @BeforeAll
    static void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> MemberManagementApplication.main(new String[]{}));
    }


}
