package com.surest.member.service;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import com.surest.member.exception.MemberAlreadyExistsException;
import com.surest.member.exception.MemberNotFoundException;
import com.surest.member.mapper.MemberMapper;
import com.surest.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    @Cacheable(value = "memberCache", key = "#id")
    @Transactional(readOnly = true)
    public MemberResponse getMemberById(UUID id) {

        log.info("Fetching member with ID: {}", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));

        return memberMapper.mapToMemberResponse(member);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<MemberResponse> getAllMembers(int page, int size, String sortBy, String sortDirection, String firstName, String lastName) {

        log.info("Fetching members | page={}, size={}, sortBy={}, direction={}", page, size, sortBy, sortDirection);

        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Member> membersPage;

        if (firstName != null && lastName != null) {
            membersPage = memberRepository
                    .findByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable);
        } else if (firstName != null) {
            membersPage = memberRepository.findByFirstNameContaining(firstName, pageable);
        } else if (lastName != null) {
            membersPage = memberRepository.findByLastNameContaining(lastName, pageable);
        } else {
            membersPage = memberRepository.findAll(pageable);
        }

        return membersPage.map(memberMapper::mapToMemberResponse);

    }


    @Override
    @Transactional
    public MemberResponse createMember(CreateMemberRequest request) {

        log.info("Creating member with email: {}", request.getEmail());

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        Member member = new Member();

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setDateOfBirth(request.getDateOfBirth());
        member.setEmail(request.getEmail());

        Member saved = memberRepository.saveAndFlush(member);

        log.info("Member created with ID: {}", saved.getId());
        return memberMapper.mapToMemberResponse(saved);

    }

    @Override
    @CacheEvict(value = "memberCache", key = "#id")
    @Transactional
    public MemberResponse updateMember(UUID id, CreateMemberRequest request) {

        log.info("Updating member with ID: {}", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));

        // Check if email is used by another member
        if (memberRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new MemberAlreadyExistsException("Email already used by another member");
        }

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setDateOfBirth(request.getDateOfBirth());
        member.setEmail(request.getEmail());

        Member updated = memberRepository.save(member);

        log.info("Member updated successfully with ID: {}", id);
        return memberMapper.mapToMemberResponse(updated);
    }

    @Override
    @CacheEvict(value = "memberCache", key = "#id")
    @Transactional
    public void deleteMember(UUID id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));

        memberRepository.delete(member);

        log.info("Member deleted with ID: {}", id);
    }

}

