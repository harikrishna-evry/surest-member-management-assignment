package com.surest.member.mapper;

import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberResponse mapToMemberResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getDateOfBirth(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
