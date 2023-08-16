package com.example.taskmanagementapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taskmanagementapp.dto.CategoryDto;
import com.example.taskmanagementapp.dto.CategoryReadDto;
import com.example.taskmanagementapp.exception.CategoryNotFoundException;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CategoryService.class})
@ExtendWith(SpringExtension.class)
@Disabled
class DiffblueCategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    /**
     * Method under test: {@link CategoryService#getAllCategories()}
     */
    @Test
    void testGetAllCategories_Empty() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(categoryService.getAllCategories().isEmpty());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryService#getAllCategories()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllCategories_OneItem() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "com.example.taskmanagementapp.model.Category.getId()" is null
        //       at com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategoryReadDto(CategoryDtoMapper.java:23)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //       at com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategoryReadDtoList(CategoryDtoMapper.java:18)
        //       at com.example.taskmanagementapp.service.CategoryService.getAllCategories(CategoryService.java:27)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1).name("Test").build());
        when(categoryRepository.findAll()).thenReturn(categoryList);
        assertEquals(1, categoryService.getAllCategories().size());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryService#getAllCategories()}
     */
    @Test
    void testGetAllCategories_ThrowsException() {
        when(categoryRepository.findAll()).thenThrow(new CategoryNotFoundException(1));
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getAllCategories());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryService#getAllCategories()}
     */
    @Test
    void testGetAllCategories4() {
        Category category = new Category();
        category.setId(1);

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryReadDto> actualAllCategories = categoryService.getAllCategories();
        assertEquals(1, actualAllCategories.size());
        CategoryReadDto getResult = actualAllCategories.get(0);
        assertEquals(1, getResult.getId());
        assertNull(getResult.getName());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryService#getAllCategories()}
     */
    @Test
    void testGetAllCategories5() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        when(category.getName()).thenReturn("Name");

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryReadDto> actualAllCategories = categoryService.getAllCategories();
        assertEquals(1, actualAllCategories.size());
        CategoryReadDto getResult = actualAllCategories.get(0);
        assertEquals(1, getResult.getId());
        assertEquals("Name", getResult.getName());
        verify(categoryRepository).findAll();
        verify(category).getId();
        verify(category).getName();
    }

    /**
     * Method under test: {@link CategoryService#getAllCategories()}
     */
    @Test
    void testGetAllCategories6() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        when(category.getName()).thenReturn("Name");

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Name", true));
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryReadDto> actualAllCategories = categoryService.getAllCategories();
        assertEquals(2, actualAllCategories.size());
        CategoryReadDto getResult = actualAllCategories.get(0);
        assertEquals("Name", getResult.getName());
        CategoryReadDto getResult2 = actualAllCategories.get(1);
        assertEquals("Name", getResult2.getName());
        assertEquals(1, getResult2.getId());
        assertEquals(1, getResult.getId());
        verify(categoryRepository).findAll();
        verify(category).getId();
        verify(category).getName();
    }

    /**
     * Method under test: {@link CategoryService#getNotHiddenCategories()}
     */
    @Test
    void testGetNotHiddenCategories() {
        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(new ArrayList<>());
        assertTrue(categoryService.getNotHiddenCategories().isEmpty());
        verify(categoryRepository).findAllByHiddenIsFalse();
    }

    /**
     * Method under test: {@link CategoryService#getNotHiddenCategories()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetNotHiddenCategories2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "com.example.taskmanagementapp.model.Category.getId()" is null
        //       at com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategoryReadDto(CategoryDtoMapper.java:23)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //       at com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategoryReadDtoList(CategoryDtoMapper.java:18)
        //       at com.example.taskmanagementapp.service.CategoryService.getNotHiddenCategories(CategoryService.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(categoryList);
        categoryService.getNotHiddenCategories();
    }

    /**
     * Method under test: {@link CategoryService#getNotHiddenCategories()}
     */
    @Test
    void testGetNotHiddenCategories3() {
        when(categoryRepository.findAllByHiddenIsFalse()).thenThrow(new CategoryNotFoundException(1));
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getNotHiddenCategories());
        verify(categoryRepository).findAllByHiddenIsFalse();
    }

    /**
     * Method under test: {@link CategoryService#getNotHiddenCategories()}
     */
    @Test
    void testGetNotHiddenCategories4() {
        Category category = new Category();
        category.setId(1);

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(categoryList);
        List<CategoryReadDto> actualNotHiddenCategories = categoryService.getNotHiddenCategories();
        assertEquals(1, actualNotHiddenCategories.size());
        CategoryReadDto getResult = actualNotHiddenCategories.get(0);
        assertEquals(1, getResult.getId());
        assertNull(getResult.getName());
        verify(categoryRepository).findAllByHiddenIsFalse();
    }

    /**
     * Method under test: {@link CategoryService#getNotHiddenCategories()}
     */
    @Test
    void testGetNotHiddenCategories5() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        when(category.getName()).thenReturn("Name");

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(categoryList);
        List<CategoryReadDto> actualNotHiddenCategories = categoryService.getNotHiddenCategories();
        assertEquals(1, actualNotHiddenCategories.size());
        CategoryReadDto getResult = actualNotHiddenCategories.get(0);
        assertEquals(1, getResult.getId());
        assertEquals("Name", getResult.getName());
        verify(categoryRepository).findAllByHiddenIsFalse();
        verify(category).getId();
        verify(category).getName();
    }

    /**
     * Method under test: {@link CategoryService#getNotHiddenCategories()}
     */
    @Test
    void testGetNotHiddenCategories6() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        when(category.getName()).thenReturn("Name");

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Name", true));
        categoryList.add(category);
        when(categoryRepository.findAllByHiddenIsFalse()).thenReturn(categoryList);
        List<CategoryReadDto> actualNotHiddenCategories = categoryService.getNotHiddenCategories();
        assertEquals(2, actualNotHiddenCategories.size());
        CategoryReadDto getResult = actualNotHiddenCategories.get(0);
        assertEquals("Name", getResult.getName());
        CategoryReadDto getResult2 = actualNotHiddenCategories.get(1);
        assertEquals("Name", getResult2.getName());
        assertEquals(1, getResult2.getId());
        assertEquals(1, getResult.getId());
        verify(categoryRepository).findAllByHiddenIsFalse();
        verify(category).getId();
        verify(category).getName();
    }

    /**
     * Method under test: {@link CategoryService#createCategory(CategoryDto)}
     */
    @Test
    void testCreateCategory() {
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        categoryService.createCategory(new CategoryDto("Name"));
        verify(categoryRepository).save(Mockito.<Category>any());
    }

    /**
     * Method under test: {@link CategoryService#createCategory(CategoryDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateCategory2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.taskmanagementapp.dto.CategoryDto.getName()" because "dto" is null
        //       at com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategory(CategoryDtoMapper.java:30)
        //       at com.example.taskmanagementapp.service.CategoryService.createCategory(CategoryService.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        categoryService.createCategory(null);
    }

    /**
     * Method under test: {@link CategoryService#createCategory(CategoryDto)}
     */
    @Test
    void testCreateCategory3() {
        when(categoryRepository.save(Mockito.<Category>any())).thenThrow(new CategoryNotFoundException(1));
        assertThrows(CategoryNotFoundException.class, () -> categoryService.createCategory(new CategoryDto("Name")));
        verify(categoryRepository).save(Mockito.<Category>any());
    }

    /**
     * Method under test: {@link CategoryService#updateCategory(int, CategoryDto)}
     */
    @Test
    void testUpdateCategory() {
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Category()));
        categoryService.updateCategory(1, new CategoryDto("Name"));
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryService#updateCategory(int, CategoryDto)}
     */
    @Test
    void testUpdateCategory2() {
        when(categoryRepository.save(Mockito.<Category>any())).thenThrow(new CategoryNotFoundException(1));
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Category()));
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1, new CategoryDto("Name")));
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryService#updateCategory(int, CategoryDto)}
     */
    @Test
    void testUpdateCategory3() {
        Category category = mock(Category.class);
        doNothing().when(category).setName(Mockito.<String>any());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        CategoryDto categoryDto = new CategoryDto("Name");
        categoryService.updateCategory(1, categoryDto);
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Integer>any());
        verify(category).setName(Mockito.<String>any());
        assertEquals("Name", categoryDto.getName());
    }

    /**
     * Method under test: {@link CategoryService#updateCategory(int, CategoryDto)}
     */
    @Test
    void testUpdateCategory4() {
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1, new CategoryDto("Name")));
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryService#updateCategory(int, CategoryDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateCategory5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.taskmanagementapp.dto.CategoryDto.getName()" because "dto" is null
        //       at com.example.taskmanagementapp.dto.mapper.CategoryDtoMapper.mapToCategory(CategoryDtoMapper.java:35)
        //       at com.example.taskmanagementapp.service.CategoryService.updateCategory(CategoryService.java:45)
        //   See https://diff.blue/R013 to resolve this issue.

        Category category = mock(Category.class);
        doNothing().when(category).setName(Mockito.<String>any());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        categoryService.updateCategory(1, null);
    }

    /**
     * Method under test: {@link CategoryService#updateCategory(int, CategoryDto)}
     */
    @Test
    void testUpdateCategory6() {
        Category category = mock(Category.class);
        doThrow(new CategoryNotFoundException(1)).when(category).setName(Mockito.<String>any());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1, new CategoryDto("Name")));
        verify(categoryRepository).findById(Mockito.<Integer>any());
        verify(category).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CategoryService#hideCategory(int)}
     */
    @Test
    void testHideCategory() {
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Category()));
        categoryService.hideCategory(1);
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryService#hideCategory(int)}
     */
    @Test
    void testHideCategory2() {
        when(categoryRepository.save(Mockito.<Category>any())).thenThrow(new CategoryNotFoundException(1));
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Category()));
        assertThrows(CategoryNotFoundException.class, () -> categoryService.hideCategory(1));
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryService#hideCategory(int)}
     */
    @Test
    void testHideCategory3() {
        Category category = mock(Category.class);
        doNothing().when(category).setHidden(anyBoolean());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        categoryService.hideCategory(1);
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Integer>any());
        verify(category).setHidden(anyBoolean());
    }

    /**
     * Method under test: {@link CategoryService#hideCategory(int)}
     */
    @Test
    void testHideCategory4() {
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.hideCategory(1));
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryService#hideCategory(int)}
     */
    @Test
    void testHideCategory5() {
        Category category = mock(Category.class);
        doThrow(new CategoryNotFoundException(1)).when(category).setHidden(anyBoolean());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        assertThrows(CategoryNotFoundException.class, () -> categoryService.hideCategory(1));
        verify(categoryRepository).findById(Mockito.<Integer>any());
        verify(category).setHidden(anyBoolean());
    }
}

