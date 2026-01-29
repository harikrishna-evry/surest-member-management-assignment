package com.surest.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    private LocalDateTime dateOfBirth;

    @Email(message = "Enter valid email")
    private String email;

}
