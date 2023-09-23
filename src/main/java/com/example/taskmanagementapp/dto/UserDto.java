package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.Role;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String username,
        Role role
) {
}
