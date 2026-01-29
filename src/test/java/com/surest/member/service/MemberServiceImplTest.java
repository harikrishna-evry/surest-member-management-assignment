package com.surest.member.service;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import com.surest.member.exception.MemberAlreadyExistsException;
import com.surest.member.exception.MemberNotFoundException;
import com.surest.member.mapper.MemberMapper;
import com.surest.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        memberMapper = new MemberMapper();
        memberService = new MemberServiceImpl(memberRepository, memberMapper);
    }


    @Test
    void testGetMemberById_Success() {
        UUID id = UUID.randomUUID();
        Member m = new Member();
        m.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(m));

        MemberResponse result = memberService.getMemberById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetMemberById_NotFound() {
        UUID id = UUID.randomUUID();

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberById(id));
    }

    @Test
    void testCreateMember_EmailExists() {
        CreateMemberRequest req = new CreateMemberRequest();
        req.setEmail("test@gmail.com");

        when(memberRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        assertThrows(MemberAlreadyExistsException.class, () -> memberService.createMember(req));
    }

    @Test
    void testCreateMember_Success() {

        CreateMemberRequest req = new CreateMemberRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("john@gmail.com");
        req.setDateOfBirth(LocalDate.now().atStartOfDay());

        when(memberRepository.existsByEmail("john@gmail.com")).thenReturn(false);

        when(memberRepository.saveAndFlush(any(Member.class))).thenAnswer(invocation -> {
            Member m = invocation.getArgument(0);
            m.setId(UUID.randomUUID());
            m.setCreatedAt(LocalDateTime.now());
            m.setUpdatedAt(LocalDateTime.now());
            return m;
        });

        MemberResponse saved = memberService.createMember(req);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("john@gmail.com");

    }

    @Test
    void testUpdateMember_NotFound() {
        UUID id = UUID.randomUUID();
        CreateMemberRequest req = new CreateMemberRequest();

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class,
                () -> memberService.updateMember(id, req));
    }

    @Test
    void testUpdateMember_EmailAlreadyUsedByAnother() {
        UUID id = UUID.randomUUID();
        CreateMemberRequest req = new CreateMemberRequest();
        req.setEmail("duplicate@gmail.com");

        Member existing = new Member();
        existing.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(existing));
        when(memberRepository.existsByEmailAndIdNot("duplicate@gmail.com", id))
                .thenReturn(true);

        assertThrows(MemberAlreadyExistsException.class,
                () -> memberService.updateMember(id, req));
    }

    @Test
    void testUpdateMember_Success() {
        UUID id = UUID.randomUUID();

        Member existing = new Member();
        existing.setId(id);

        CreateMemberRequest req = new CreateMemberRequest();
        req.setFirstName("Updated");
        req.setLastName("User");
        req.setEmail("updated@gmail.com");
        req.setDateOfBirth(LocalDate.now().atStartOfDay());

        when(memberRepository.findById(id)).thenReturn(Optional.of(existing));
        when(memberRepository.existsByEmailAndIdNot("updated@gmail.com", id))
                .thenReturn(false);
        when(memberRepository.save(any(Member.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        MemberResponse updated = memberService.updateMember(id, req);

        assertEquals("Updated", updated.getFirstName());
        assertEquals("User", updated.getLastName());
        assertEquals("updated@gmail.com", updated.getEmail());
        verify(memberRepository, times(1)).save(existing);
    }

    @Test
    void testDeleteMember_NotFound() {
        UUID id = UUID.randomUUID();

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class,
                () -> memberService.deleteMember(id));
    }

    @Test
    void testDeleteMember_Success() {
        UUID id = UUID.randomUUID();
        Member m = new Member();
        m.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(m));

        memberService.deleteMember(id);

        verify(memberRepository, times(1)).delete(m);
    }

    @Test
    void testGetAllMembers_NoFilters() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName").ascending());

        Page<Member> memberPage = mock(Page.class);

        when(memberRepository.findAll(any(Pageable.class))).thenReturn(memberPage);

        when(memberPage.map(any())).thenReturn(Page.empty());

        Page<MemberResponse> result =
                memberService.getAllMembers(0, 10, "firstName", "asc", null, null);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());

        verify(memberRepository, times(1)).findAll(any(Pageable.class));
    }


    @Test
    void testGetAllMembers_WithFirstNameAndLastName() {

        Page<Member> memberPage = mock(Page.class);

        when(memberRepository.findByFirstNameContainingAndLastNameContaining(
                eq("John"), eq("Doe"), any(Pageable.class)
        )).thenReturn(memberPage);

        when(memberPage.map(any())).thenReturn(Page.empty());

        Page<MemberResponse> result =
                memberService.getAllMembers(0, 10, "firstName", "asc", "John", "Doe");

        assertNotNull(result);
        verify(memberRepository, times(1))
                .findByFirstNameContainingAndLastNameContaining(eq("John"), eq("Doe"), any(Pageable.class));
    }

    @Test
    void testGetAllMembers_WithFirstNameOnly() {

        Page<Member> memberPage = mock(Page.class);

        when(memberRepository.findByFirstNameContaining(eq("John"), any(Pageable.class)))
                .thenReturn(memberPage);

        when(memberPage.map(any())).thenReturn(Page.empty());

        Page<MemberResponse> result =
                memberService.getAllMembers(0, 10, "firstName", "asc", "John", null);

        assertNotNull(result);
        verify(memberRepository, times(1))
                .findByFirstNameContaining(eq("John"), any(Pageable.class));
    }

    @Test
    void testGetAllMembers_WithLastNameOnly() {

        Page<Member> memberPage = mock(Page.class);

        when(memberRepository.findByLastNameContaining(eq("Doe"), any(Pageable.class)))
                .thenReturn(memberPage);

        when(memberPage.map(any())).thenReturn(Page.empty());

        Page<MemberResponse> result =
                memberService.getAllMembers(0, 10, "firstName", "asc", null, "Doe");

        assertNotNull(result);
        verify(memberRepository, times(1))
                .findByLastNameContaining(eq("Doe"), any(Pageable.class));
    }


}
