package com.example.taskmanagementapp.dto.mapper;


import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.model.Category;

public class CategoryDtoMapper {

    private CategoryDtoMapper() {
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category mapToCategoryCreate(CategoryDto dto) {
        return Category.builder()
                .name(dto.name())
                .build();
    }

    public static Category mapToCategoryUpdate(CategoryDto dto, Category toUpdate) {
        toUpdate.setName(dto.name());
        return toUpdate;
    }
}
