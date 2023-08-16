package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.CategoryReadDto;
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

    public List<CategoryReadDto> getAllCategories() {
        return mapToCategoryReadDtoList(categoryRepository.findAll());
    }

    public List<CategoryReadDto> getNotHiddenCategories() {
        return mapToCategoryReadDtoList(categoryRepository.findAllByHiddenIsFalse());
    }

    @Transactional
    public CategoryReadDto createCategory(CategoryDto categoryDto) {
        Category created = categoryRepository.save(mapToCategory(categoryDto));
        return mapToCategoryReadDto(created);
    }

    @Transactional
    public CategoryReadDto updateCategory(int id, CategoryDto categoryDto) {
        Category toUpdate = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(id)
        );

        Category updated = categoryRepository.save(mapToCategory(categoryDto, toUpdate));
        return mapToCategoryReadDto(updated);
    }

    @Transactional
    public CategoryReadDto hideCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(id)
        );

        category.setHidden(true);
        Category updated = categoryRepository.save(category);
        return mapToCategoryReadDto(updated);
    }
}
