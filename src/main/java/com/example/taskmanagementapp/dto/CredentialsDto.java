package com.example.taskmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CredentialsDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotEmpty(message = "Password is required")
        char[] password
) {
}
