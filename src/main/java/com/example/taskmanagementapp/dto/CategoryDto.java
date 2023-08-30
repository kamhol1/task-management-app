package com.example.taskmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Integer id,
        @NotBlank(message = "Category name must not be blank")
        String name
) {
}
