package com.surest.member.repository;

import com.surest.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Page<Member> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName, Pageable pageable);

    Page<Member> findByFirstNameContaining(String firstName, Pageable pageable);

    Page<Member> findByLastNameContaining(String lastName, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

}
