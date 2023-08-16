package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.CategoryReadDto;
import com.example.taskmanagementapp.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<CategoryReadDto>> getNotHiddenCategories() {
        return ResponseEntity.ok(
                categoryService.getNotHiddenCategories());
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryReadDto created = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(new MessageResponse("Category \"" + created.getName() + "\" created successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateCategory(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
        CategoryReadDto updated = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(new MessageResponse("Category with id " + updated.getId() + " has been updated successfully"));
    }

    @PatchMapping("/{id}/hide")
    ResponseEntity<MessageResponse> hideCategory(@PathVariable int id) {
        CategoryReadDto updated = categoryService.hideCategory(id);
        return ResponseEntity.ok(new MessageResponse("Category with id " + updated.getId() + " has been deleted"));
    }
}
