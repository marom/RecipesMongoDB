package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.repositories.reactive.RecipeReactiveRepository;
import com.marom.recipemongo.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    private RecipeReactiveRepository recipeRepository;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void whenFindByRecipeIdAndIngredientIdThenCorrect() {

        //given
        Recipe recipe = Recipe.builder().id("recipe1").build();
        Ingredient salt = Ingredient.builder().id("salt1").build();
        Ingredient pepper = Ingredient.builder().id("pepper").build();
        Ingredient water = Ingredient.builder().id("water").build();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(salt);
        ingredients.add(pepper);
        ingredients.add(water);
        recipe.setIngredients(ingredients);

        Mono<Recipe> recipeMono = Mono.just(recipe);


        //when
        when(recipeRepository.findById(anyString())).thenReturn(recipeMono);

        Ingredient foundIngredient = ingredientService.findByRecipeIdAndIngredientId("recipe1", "salt1").block();

        //then
        assertEquals(foundIngredient.getId(), salt.getId());
    }

    @Test
    public void testSaveForUpdateIngredient() {
        //given
        UnitOfMeasure uom = UnitOfMeasure.builder().id("uom1").description("Pinch").build();

        Ingredient ingredient = new Ingredient();
        ingredient.setId("ingr1");
        ingredient.setDescription("ingr desc");
        ingredient.setAmount(new BigDecimal("5"));
        ingredient.setRecipeId("rec1");
        ingredient.setUom(uom);


        Mono<Recipe> recipeMono = Mono.just(new Recipe());
        recipeMono.block().setIngredients(new HashSet<>(Arrays.asList(ingredient)));

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(ingredient);
        savedRecipe.getIngredients().iterator().next().setId("ingr1");

        when(recipeRepository.findById(anyString())).thenReturn(recipeMono);
        when(unitOfMeasureRepository.findById(anyString())).thenReturn(Mono.just(uom));
        when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient).block();

        //then
        assertEquals("ingr1", savedIngredient.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testSaveNewIngredient() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription("ingr desc");
        ingredient.setAmount(new BigDecimal("5"));
        ingredient.setRecipeId("rec1");

        Mono<Recipe> recipeMono = Mono.just(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyString())).thenReturn(recipeMono);
        when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient).block();

        //then
        assertEquals(ingredient.getId(), savedIngredient.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void deleteIngredientById() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("ingr23");
        recipe.addIngredient(ingredient);
        Mono<Recipe> recipeMono = Mono.just(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeMono);
        when(recipeRepository.save(any())).thenReturn(Mono.empty());

        //when
        ingredientService.deleteById("rec2", "ingr23").block();

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

}
