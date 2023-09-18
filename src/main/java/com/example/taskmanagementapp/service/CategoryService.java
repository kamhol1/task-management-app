package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper;
import com.example.taskmanagementapp.exception.CategoryNotFoundException;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getNotHiddenCategories() {
        return categoryRepository.findAllByHiddenIsFalse().stream()
                .map(CategoryDtoMapper::mapToCategoryDto)
                .toList();
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category created = categoryRepository.save(mapToCategoryCreate(categoryDto));
        return mapToCategoryDto(created);
    }

    @Transactional
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Category toUpdate = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(id)
        );

        Category updated = categoryRepository.save(mapToCategoryUpdate(categoryDto, toUpdate));
        return mapToCategoryDto(updated);
    }

    @Transactional
    public CategoryDto hideCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(id)
        );

        category.setHidden(true);
        Category updated = categoryRepository.save(category);
        return mapToCategoryDto(updated);
    }
}
