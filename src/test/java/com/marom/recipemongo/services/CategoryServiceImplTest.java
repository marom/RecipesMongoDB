package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }


    @Test
    public void whenGetAllCategoriesThenCorrect() {

        // given
        Category categoryMexican = Category.builder().id("catM").description("Mexican").build();
        Category categoryAsian = Category.builder().id("catA").description("Asian").build();
        Category categoryPolish = Category.builder().id("catP").description("Polish").build();

        Set<Category> categories = new HashSet<>();
        categories.add(categoryMexican);
        categories.add(categoryAsian);
        categories.add(categoryPolish);

        // when
        when(categoryRepository.findAll()).thenReturn(categories);

        Set<Category> allCategories = categoryService.getAllCategories();

        // then
        assertThat(allCategories, not(empty()));
        assertThat(allCategories, hasSize(3));
    }

    @Test
    public void whenNoCategoriesFoundThenReturnEmpty() {

        // given
        Set<Category> categories = new HashSet<>();

        // when
        when(categoryRepository.findAll()).thenReturn(categories);

        Set<Category> allCategories = categoryService.getAllCategories();

        // then
        assertThat(allCategories, hasSize(0));
    }
}
