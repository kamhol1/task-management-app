package com.example.taskmanagementapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryReadDto {
    private int id;
    private String name;
}
