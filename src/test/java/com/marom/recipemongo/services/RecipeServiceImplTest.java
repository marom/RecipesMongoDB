package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
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
}
