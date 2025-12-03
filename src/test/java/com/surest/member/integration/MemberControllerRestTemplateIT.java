package com.surest.member.integration;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(NoSecurityConfig.class)
class MemberControllerRestTemplateIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void createMember_persists_andReturns201() {
        CreateMemberRequest req = new CreateMemberRequest();
        req.setFirstName("Jane");
        req.setLastName("Smith");
        req.setEmail("jane.smith@example.com");
        req.setDateOfBirth(LocalDateTime.of(1990, 1, 1, 0, 0));

        ResponseEntity<Member> created = rest.postForEntity("/api/v1/members", req, Member.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Member body = created.getBody();
        assertThat(body).isNotNull();
        UUID id = body.getId();
        assertThat(id).isNotNull();

        Member fetched = rest.getForObject("/api/v1/members/{id}", Member.class, id);
        assertThat(fetched.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(fetched.getFirstName()).isEqualTo("Jane");
    }

    @Test
    @Sql(statements = "INSERT INTO member (id, first_name, last_name, date_of_birth, email, created_at, updated_at) " +
            "VALUES ('00000000-0000-0000-0000-000000000010','Old','Name','1980-01-01','old@example.com', NOW(), NOW())")
    void updateMember_changesPersisted() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000010");

        CreateMemberRequest update = new CreateMemberRequest();
        update.setFirstName("New");
        update.setLastName("Name");
        update.setEmail("new@example.com");
        update.setDateOfBirth(LocalDateTime.of(1980, 1, 1, 0, 0));

        rest.put("/api/v1/members/{id}", update, id);

        Member fetched = rest.getForObject("/api/v1/members/{id}", Member.class, id);
        assertThat(fetched.getFirstName()).isEqualTo("New");
        assertThat(fetched.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    @Sql(statements = "INSERT INTO member (id, first_name, last_name, date_of_birth, email, created_at, updated_at) " +
            "VALUES ('00000000-0000-0000-0000-000000000020','Delete','Me','1970-01-01','del@example.com', NOW(), NOW())")
    void deleteMember_removesEntity() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000020");

        rest.delete("/api/v1/members/{id}", id);

        ResponseEntity<String> after = rest.getForEntity("/api/v1/members/{id}", String.class, id);

        assertThat(after.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

