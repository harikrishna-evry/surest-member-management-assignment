package com.surest.member.controller;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import com.surest.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MemberControllerTest {


    private MemberService memberService;
    private MemberController controller;

    @BeforeEach
    void setup() {
        memberService = mock(MemberService.class);
        controller = new MemberController(memberService);  // WORKS NOW
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        MemberResponse mockMember = new MemberResponse();
        mockMember.setId(id);

        when(memberService.getMemberById(id)).thenReturn(mockMember);

        MemberResponse response = controller.findById(id).getBody();

        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(memberService, times(1)).getMemberById(id);
    }

    @Test
    void testCreateMember() {
        CreateMemberRequest req = new CreateMemberRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("john@gmail.com");
       req.setDateOfBirth(LocalDate.now().atStartOfDay());


        MemberResponse saved = new MemberResponse();
        saved.setId(UUID.randomUUID());
        saved.setFirstName("John");

        when(memberService.createMember(any(CreateMemberRequest.class)))
                .thenReturn(saved);

        MemberResponse response = controller.createMember(req).getBody();

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        verify(memberService, times(1)).createMember(any(CreateMemberRequest.class));
    }

    @Test
    void testUpdateMember() {
        UUID id = UUID.randomUUID();

        CreateMemberRequest req = new CreateMemberRequest();
        req.setFirstName("Updated");

        MemberResponse updatedMember = new MemberResponse();
        updatedMember.setId(id);
        updatedMember.setFirstName("Updated");

        when(memberService.updateMember(eq(id), any(CreateMemberRequest.class)))
                .thenReturn(updatedMember);

        MemberResponse response = controller.updateMember(id, req).getBody();

        assertNotNull(response);
        assertEquals("Updated", response.getFirstName());
        verify(memberService, times(1)).updateMember(eq(id), any(CreateMemberRequest.class));
    }

    @Test
    void testDeleteMember() {
        UUID id = UUID.randomUUID();

        doNothing().when(memberService).deleteMember(id);

        controller.deleteMember(id);

        verify(memberService, times(1)).deleteMember(id);
    }

}
