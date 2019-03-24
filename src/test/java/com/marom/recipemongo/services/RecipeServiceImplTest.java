package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeRepository;

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

       // Set<Recipe> recipes = new HashSet<>();
        Flux<Recipe> recipeFlux = Flux.just(recipe1,recipe2);
       // recipes.add(recipe1);
       // recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipeFlux);

        Flux<Recipe> allRecipes = recipeService.getAllRecipes();

        assertNotNull(allRecipes);
        //assertThat(allRecipes., hasSize(2));

        verify(recipeRepository, times(1)).findAll();
    }

    @Ignore("how to count Flux???")
    @Test
    public void getAllRecipesWhenNorRecipesShouldReturnEmptyList() {

        Flux<Recipe> recipes = Flux.empty();

        when(recipeRepository.findAll()).thenReturn(recipes);

        final Flux<Recipe> allRecipes = recipeService.getAllRecipes();

        //assertThat(allRecipes.count(), hasSize(0));

        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    public void getRecipeByIdTest() {

        Mono<Recipe> recipe = Mono.just(Recipe.builder().id("abcd1234").build());
        //Optional<Recipe> recipeOptional = Optional.of(recipe);


        when(recipeRepository.findById(anyString())).thenReturn(recipe);

        Recipe recipeReturned = recipeService.findById("abcd1234").block();

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeByIdTestNotFound() {

        Mono<Recipe> recipeMono = Mono.empty();

        when(recipeRepository.findById(anyString())).thenReturn(recipeMono);

        Mono<Recipe> recipe = recipeService.findById("wrongId");

        assertEquals(recipe, recipeMono);
    }
}
