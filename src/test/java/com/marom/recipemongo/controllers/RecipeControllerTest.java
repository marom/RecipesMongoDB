package com.marom.recipemongo.controllers;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                //for global exception handler
              //  .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }


    @Test
    public void showRecipe() throws Exception {

        Recipe recipe = Recipe.builder().id("23er56").build();

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
                .andExpect(view().name("404error"));
    }
}
