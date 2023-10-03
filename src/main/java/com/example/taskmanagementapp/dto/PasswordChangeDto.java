package com.example.taskmanagementapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PasswordChangeDto(
        String currentPassword,
        @NotEmpty(message = "Password is required and should contain at least 8 characters")
        @Size(min = 8, message = "Password is required and should contain at least 8 characters")
        String newPassword,
        String newPasswordConfirm
) {
}
