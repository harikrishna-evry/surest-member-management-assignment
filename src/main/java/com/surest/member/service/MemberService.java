package com.surest.member.service;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MemberService {

    public Member getMemberById(UUID id);
    public Page<Member> getAllMembers(int page, int size, String sortBy, String sortDirection, String firstName, String lastName);
    public Member createMember(CreateMemberRequest request);
    public Member updateMember(UUID id, CreateMemberRequest request);
    public void deleteMember(UUID id);

}
