package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.exception.CategoryNotFoundException;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getNotHiddenCategories_returnsEmptyList() {
        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(new ArrayList<>());

        assertTrue(categoryService.getNotHiddenCategories().isEmpty());
        verify(categoryRepository).findAllByHiddenIsFalse();
    }

    @Test
    void getNotHiddenCategories_returnsOneElementList() {
        List<Category> list = new ArrayList<>();
        list.add(new Category());

        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(list);

        assertEquals(1, categoryService.getNotHiddenCategories().size());
        verify(categoryRepository).findAllByHiddenIsFalse();
    }

    @Test
    void getNotHiddenCategories_returnsOnlyNotHiddenCategories() {
        List<Category> list = new ArrayList<>();
        list.add(Category.builder()
                .id(1)
                .name("Category1")
                .hidden(true)
                .build());
        list.add(Category.builder()
                .id(2)
                .name("Category2")
                .build());

        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(list);

        List<CategoryDto> result = categoryService.getNotHiddenCategories();
        assertEquals(2, result.size());
        assertEquals(2, result.get(1).id());
        verify(categoryRepository).findAllByHiddenIsFalse();
    }

    @Test
    void createCategory_returnsCategoryDto() {
        Category category = new Category();
        category.setName("Test category");
        CategoryDto dto = new CategoryDto(null, "Test category");

        when(categoryRepository.save(any())).thenReturn(category);

        CategoryDto created = categoryService.createCategory(dto);
        assertEquals(dto, created);
        verify(categoryRepository).save(any());
    }

    @Test
    void updateCategory_returnsCategoryDto() {
        Category category = new Category();
        category.setId(1);
        category.setName("Category name");
        CategoryDto dto = new CategoryDto(category.getId(), "Updated name");

        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        assertEquals(dto, categoryService.updateCategory(category.getId(), dto));
        verify(categoryRepository).findById(any());
        verify(categoryRepository).save(any());
    }

    @Test
    void updateCategory_throwsCategoryNotFoundException() {
        CategoryDto dto = mock(CategoryDto.class);

        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1, dto));
        verify(categoryRepository).findById(any());
    }

    @Test
    void hideCategory_hiddenIsTrue() {
        Category category = new Category();
        category.setId(1);
        category.setHidden(true);
        CategoryDto dto = new CategoryDto(category.getId(), category.getName());

        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        assertEquals(dto, categoryService.hideCategory(1));
        verify(categoryRepository).findById(any());
        verify(categoryRepository).save(any());
    }

    @Test
    void hideCategory_throwsCategoryNotFoundException() {
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.hideCategory(1));
        verify(categoryRepository).findById(any());
    }
}