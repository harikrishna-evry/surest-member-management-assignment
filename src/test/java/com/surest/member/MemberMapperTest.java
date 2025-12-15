package com.surest.member;
import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import com.surest.member.mapper.MemberMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperTest {

    private final MemberMapper mapper = new MemberMapper();

    @Test
    void testMapMemberToMemberResponse() {

        Member m = new Member();
        m.setId(UUID.randomUUID());
        m.setFirstName("John");
        m.setLastName("Doe");
        m.setEmail("john@gmail.com");
        m.setCreatedAt(LocalDateTime.now());
        m.setUpdatedAt(LocalDateTime.now());

        MemberResponse response = mapper.mapToMemberResponse(m);

        assertEquals(m.getId(), response.getId());
        assertEquals(m.getFirstName(), response.getFirstName());
        assertEquals(m.getLastName(), response.getLastName());
        assertEquals(m.getEmail(), response.getEmail());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
    }
}

