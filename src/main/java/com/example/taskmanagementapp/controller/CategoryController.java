package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.CategoryReadDto;
import com.example.taskmanagementapp.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    List<CategoryReadDto> getNotHiddenCategories() {
        return categoryService.getNotHiddenCategories();
    }

    @PostMapping("")
    void createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
    }

    @PutMapping("/{id}")
    void updateCategory(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(id, categoryDto);
    }

    @PatchMapping("/{id}/hide")
    void hideCategory(@PathVariable int id) {
        categoryService.hideCategory(id);
    }
}
