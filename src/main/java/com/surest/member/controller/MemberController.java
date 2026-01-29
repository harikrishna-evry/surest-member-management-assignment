package com.surest.member.controller;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.dto.MemberResponse;
import com.surest.member.entity.Member;
import com.surest.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable UUID id) {

        log.info("Received request: GET /api/v1/members/{}", id);

        MemberResponse member = memberService.getMemberById(id);

        return ResponseEntity.ok().body(member);

    }


    @GetMapping
    public Page<MemberResponse> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        log.info(
                "Received request: GET /api/v1/members?page={}&size={}&sortBy={}&direction={} firstName={} lastName={}",
                page, size, sortBy, sortDirection, firstName, lastName
        );

        return memberService.getAllMembers(page, size, sortBy, sortDirection, firstName, lastName);

    }


    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        log.info("Received request: POST /api/v1/members");

        MemberResponse member = memberService.createMember(request);

        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable UUID id, @Valid @RequestBody CreateMemberRequest request) {
        log.info("Received request: PUT /api/v1/members/{}", id);

        MemberResponse updated = memberService.updateMember(id, request);

        log.info("Member updated: {}", id);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        log.info("Received request: DELETE /api/v1/members/{}", id);

        memberService.deleteMember(id);

        log.info("Member deleted: {}", id);
        return ResponseEntity.noContent().build();   // 204 No Content
    }

}
