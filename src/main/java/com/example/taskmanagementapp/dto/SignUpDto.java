package com.example.taskmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        String firstName,
        String lastName,
        @NotBlank(message = "Username should contain at least 5 characters")
        @Size(min = 5, message = "Username should contain at least 5 characters")
        @Size(max = 30, message = "Username should contain a maximum of 30 characters")
        String username,
        @NotEmpty(message = "Password is required and should contain at least 8 characters")
        @Size(min = 8, message = "Password is required and should contain at least 8 characters")
        char[] password,
        @NotEmpty(message = "Password confirmation is required")
        char[] passwordConfirmation
) {
}