package com.surest.member.service;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MemberService {

    public MemberResponse getMemberById(UUID id);
    public Page<MemberResponse> getAllMembers(int page, int size, String sortBy, String sortDirection, String firstName, String lastName);
    public MemberResponse createMember(CreateMemberRequest request);
    public MemberResponse updateMember(UUID id, CreateMemberRequest request);
    public void deleteMember(UUID id);

}
