package com.marom.recipemongo.controllers;

import com.marom.recipemongo.converters.CategoryToCategoryDto;
import com.marom.recipemongo.converters.RecipeToRecipeDto;
import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.dto.RecipeDto;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.services.CategoryService;
import com.marom.recipemongo.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private RecipeToRecipeDto recipeToRecipeDto;

    @Mock
    private CategoryToCategoryDto categoryToCategoryDto;

    @InjectMocks
    private RecipeController recipeController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                //for global exception handler
              //  .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showRecipe() throws Exception {

        Mono<Recipe> recipe = Mono.just(Recipe.builder().id("23er56").build());

        when(recipeService.findById(anyString())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/23er56/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/viewRecipe"));
    }

    @Test
    public void showRecipeNotFoundException() throws Exception {

        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/weeref34434/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }


//    @Test
//    public void showRecipeJUnit5ThrowException() {
//
//        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
//
//        assertThrows(NotFoundException.class, () -> recipeService.findById(anyString()));
//    }

    @Test
    public void showRecipeForEdit() throws Exception {

        Mono<Recipe> recipe = Mono.just(Recipe.builder().id("qwerty").build());

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId("qwerty");

        Category categoryMexican = Category.builder().id("catM").description("Mexican").build();
        Category categoryAsian = Category.builder().id("catA").description("Asian").build();
        Category categoryPolish = Category.builder().id("catP").description("Polish").build();

        Set<Category> categories = new HashSet<>();
        categories.add(categoryMexican);
        categories.add(categoryAsian);
        categories.add(categoryPolish);

        when(recipeService.findById(anyString())).thenReturn(recipe);
        when(recipeToRecipeDto.convert(recipe.block())).thenReturn(recipeDto);
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/recipe/qwerty/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", hasProperty("id", is("qwerty"))))
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(view().name("recipe/editRecipe"));
    }
}
