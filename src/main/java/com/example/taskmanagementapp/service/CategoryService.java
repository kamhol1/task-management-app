package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.CategoryReadDto;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategory;
import static com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategoryReadDtoList;

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
    public void createCategory(CategoryDto categoryDto) {
        categoryRepository.save(mapToCategory(categoryDto));
    }

    @Transactional
    public void updateCategory(int id, CategoryDto categoryDto) {
        Category toUpdate = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found")
        );

        categoryRepository.save(mapToCategory(categoryDto, toUpdate));
    }

    @Transactional
    public void hideCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found")
        );

        category.setHidden(true);
        categoryRepository.save(category);
    }
}
