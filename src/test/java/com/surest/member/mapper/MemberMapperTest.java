package com.surest.member.mapper;

import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperTest {

    private final MemberMapper mapper = new MemberMapper();

    @Test
    void testMapToMemberResponse() {

        UUID id = UUID.randomUUID();
        LocalDateTime dob = LocalDateTime.of(1998, 7, 25, 10, 0);
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();

        Member member = new Member();
        member.setId(id);
        member.setFirstName("Divya");
        member.setLastName("Menon");
        member.setDateOfBirth(dob);
        member.setEmail("divya@example.com");
        member.setCreatedAt(created);
        member.setUpdatedAt(updated);
        member.setVersion(1L);

        MemberResponse response = mapper.mapToMemberResponse(member);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("Divya", response.getFirstName());
        assertEquals("Menon", response.getLastName());
        assertEquals(dob, response.getDateOfBirth());
        assertEquals("divya@example.com", response.getEmail());
        assertEquals(created, response.getCreatedAt());
        assertEquals(updated, response.getUpdatedAt());
    }


    @Test
    void testMapToMemberResponse_NullMember_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToMemberResponse(null));
    }

}
