package com.surest.member.integration;

import com.surest.member.dto.CreateMemberRequest;
import com.surest.member.entity.Member;
import com.surest.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"user.timezone=Asia/Kolkata"})
@ActiveProfiles("test")
public class MemberControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    static {
        System.setProperty("user.timezone", "Asia/Kolkata");
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
    }

    @BeforeEach
    void cleanDb() {
        memberRepository.deleteAll();
    }



    @Test
    void findById_shouldReturnMember() throws Exception {


        LocalDateTime dob = LocalDateTime.of(1998, 5, 3, 0, 0);

        Member member = new Member();
        member.setFirstName("Hari");
        member.setLastName("Krishna");
        member.setDateOfBirth(dob);
        member.setEmail("hari@test.com");

        memberRepository.saveAndFlush(member);

        mockMvc.perform(
                        get("/api/v1/members/" + member.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(member.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("Hari"))
                .andExpect(jsonPath("$.lastName").value("Krishna"))
                .andExpect(jsonPath("$.dateOfBirth").value(dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
                .andExpect(jsonPath("$.email").value("hari@test.com"));
    }


    @Test
    void getAllMembers_shouldReturnPagedResults() throws Exception {

        for (int i = 1; i <= 3; i++) {

            Member m = new Member();
            m.setFirstName("First" + i);
            m.setLastName("Last" + i);
            m.setEmail("user" + i + "@test.com");
            m.setDateOfBirth(LocalDateTime.of(1990, 1, i, 0, 0));

            memberRepository.save(m);
        }

        mockMvc.perform(
                        get("/api/v1/members?page=0&size=2&sortBy=lastName&sortDirection=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2)) // page size 2
                .andExpect(jsonPath("$.totalElements").value(3));
    }


    @Test
    void createMember_shouldReturnCreatedMember() throws Exception {

        CreateMemberRequest request = new CreateMemberRequest();

        request.setFirstName("New");
        request.setLastName("Member");
        request.setEmail("newmember@test.com");
        request.setDateOfBirth(LocalDateTime.of(1995, 8, 10, 0, 0));

        mockMvc.perform(
                        post("/api/v1/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("New"))
                .andExpect(jsonPath("$.lastName").value("Member"))
                .andExpect(jsonPath("$.email").value("newmember@test.com"));
    }


    @Test
    void updateMember_shouldModifyFields() throws Exception {

        Member existing = new Member();

        existing.setFirstName("Old");
        existing.setLastName("User");
        existing.setEmail("old@test.com");
        existing.setDateOfBirth(LocalDateTime.of(1990, 1, 1, 0, 0));

        memberRepository.saveAndFlush(existing);

        CreateMemberRequest updateReq = new CreateMemberRequest();

        updateReq.setFirstName("Updated");
        updateReq.setLastName("User");
        updateReq.setEmail("updated@test.com");
        updateReq.setDateOfBirth(LocalDateTime.of(1991, 2, 2, 0, 0));

        mockMvc.perform(
                        put("/api/v1/members/" + existing.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.email").value("updated@test.com"));
    }

    @Test
    void deleteMember_shouldRemoveMember() throws Exception {

        Member member = new Member();

        member.setFirstName("Delete");
        member.setLastName("Me");
        member.setEmail("delete@test.com");
        member.setDateOfBirth(LocalDateTime.of(1990, 1, 1, 0, 0));

        memberRepository.saveAndFlush(member);

        mockMvc.perform(delete("/api/v1/members/" + member.getId()))
                .andExpect(status().isNoContent());

        assert (!memberRepository.findById(member.getId()).isPresent());
    }



}
