package com.example.taskmanagementapp.dto.mapper;


import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.CategoryReadDto;
import com.example.taskmanagementapp.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDtoMapper {

    private CategoryDtoMapper() {}

    public static List<CategoryReadDto> mapToCategoryReadDtoList(List<Category> categories) {
        return categories.stream()
                .map(CategoryDtoMapper::mapToCategoryReadDto)
                .collect(Collectors.toList());
    }

    public static CategoryReadDto mapToCategoryReadDto(Category category) {
        return CategoryReadDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapToCategory(CategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public static Category mapToCategory(CategoryDto dto, Category toUpdate) {
        toUpdate.setName(dto.getName());
        return toUpdate;
    }

}
