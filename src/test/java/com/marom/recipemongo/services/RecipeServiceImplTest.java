package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.repositories.RecipeRepository;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    private RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getAllRecipes() {

        Recipe recipe1 = new Recipe();
        recipe1.setId("12sdfdf546767");

        Recipe recipe2 = new Recipe();
        recipe2.setId("12sdfdf546767");

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> allRecipes = recipeService.getAllRecipes();

        assertNotNull(allRecipes);
        assertThat(allRecipes, hasSize(2));

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getAllRecipesWhenNorRecipesShouldReturnEmptyList() {

        Set<Recipe> recipes = new HashSet<>();

        when(recipeRepository.findAll()).thenReturn(recipes);

        assertThat(recipeService.getAllRecipes(), hasSize(0));

        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("abcd1234");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById("abcd1234");

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception {

        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        recipeService.findById("wrongId");
    }
}
