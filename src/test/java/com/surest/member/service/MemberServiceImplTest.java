package com.surest.member.service;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.entity.Member;
import com.surest.member.exception.MemberAlreadyExistsException;
import com.surest.member.exception.MemberNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetMemberById_Success() {
        UUID id = UUID.randomUUID();
        Member m = new Member();
        m.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(m));

        Member result = memberService.getMemberById(id);

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
        when(memberRepository.save(any(Member.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Member result = memberService.createMember(req);

        assertEquals("John", result.getFirstName());
        verify(memberRepository, times(1)).save(any(Member.class));
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

        Member updated = memberService.updateMember(id, req);

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

        Page<Member> page = mock(Page.class);

        when(memberRepository.findAll(pageable)).thenReturn(page);

        Page<Member> result = memberService.getAllMembers(0, 10, "firstName", "asc", null, null);

        assertNotNull(result);
        verify(memberRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllMembers_FilterByFirstName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName").ascending());
        Page<Member> page = mock(Page.class);

        when(memberRepository.findByFirstNameContaining("John", pageable))
                .thenReturn(page);

        Page<Member> result = memberService.getAllMembers(0, 10, "firstName", "asc", "John", null);

        assertNotNull(result);
        verify(memberRepository).findByFirstNameContaining("John", pageable);
    }

    @Test
    void testGetAllMembers_FilterByLastName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName").ascending());
        Page<Member> page = mock(Page.class);

        when(memberRepository.findByLastNameContaining("Doe", pageable))
                .thenReturn(page);

        Page<Member> result = memberService.getAllMembers(0, 10, "firstName", "asc", null, "Doe");

        assertNotNull(result);
        verify(memberRepository).findByLastNameContaining("Doe", pageable);
    }

    @Test
    void testGetAllMembers_FilterByBoth() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName").ascending());
        Page<Member> page = mock(Page.class);

        when(memberRepository.findByFirstNameContainingAndLastNameContaining("John", "Doe", pageable))
                .thenReturn(page);

        Page<Member> result = memberService.getAllMembers(0, 10, "firstName", "asc", "John", "Doe");

        assertNotNull(result);
        verify(memberRepository).findByFirstNameContainingAndLastNameContaining("John", "Doe", pageable);
    }


}
