package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        @NotBlank(message = "Username should contain at least 5 characters")
        @Size(min = 5, message = "Username should contain at least 5 characters")
        @Size(max = 30, message = "Username should contain a maximum of 30 characters")
        String username,
        @NotNull(message = "User role must not be null")
        Role role,
        boolean enabled
) {
}
