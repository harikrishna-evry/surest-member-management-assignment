package com.surest.member.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class MemberControllerMockMvcIT {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(statements = "INSERT INTO member (id, first_name, last_name, date_of_birth, email, created_at, updated_at) " +
            "VALUES ('00000000-0000-0000-0000-000000000001','John','Doe','1990-01-01','john.doe@example.com', NOW(), NOW())")
    void findById_returns200_andMember() throws Exception {

        mockMvc.perform(get("/api/v1/members/{id}", "00000000-0000-0000-0000-000000000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("00000000-0000-0000-0000-000000000001"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void findById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/v1/members/{id}", "00000000-0000-0000-0000-999999999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(statements = {
            "INSERT INTO member (id, first_name, last_name, date_of_birth, email, created_at, updated_at) VALUES " +
                    "('00000000-0000-0000-0000-000000000002','Alice','Brown','1991-02-02','alice@example.com', NOW(), NOW())",
            "INSERT INTO member (id, first_name, last_name, date_of_birth, email, created_at, updated_at) VALUES " +
                    "('00000000-0000-0000-0000-000000000003','Bob','Anderson','1992-03-03','bob@example.com', NOW(), NOW())"
    })
    void getAllMembers_pagination_sorting_filtering() throws Exception {
        mockMvc.perform(get("/api/v1/members")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "lastName")
                        .param("sortDirection", "asc")
                        .param("firstName", "Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].firstName").value("Alice"))
                .andExpect(jsonPath("$.content[0].lastName").value("Brown"));
    }

    @Test
    void createMember_validationErrors_returns400() throws Exception {
        String badJson = """
                { "firstName": "", "lastName": "", "email": "invalid", "dateOfBirth": null }
                """;
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }
}
