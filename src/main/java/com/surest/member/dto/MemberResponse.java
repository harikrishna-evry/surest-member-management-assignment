package com.surest.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
