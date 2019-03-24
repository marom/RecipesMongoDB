package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.repositories.RecipeRepository;
import com.marom.recipemongo.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

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

        Optional<Recipe> recipeOptional = Optional.of(recipe);


        //when
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Ingredient foundIngredient = ingredientService.findByRecipeIdAndIngredientId("recipe1", "salt1");

        //then
        assertEquals(foundIngredient.getId(), salt.getId());
    }

    @Test(expected = NotFoundException.class)
    public void whenRecipeNotFoundThenNotFoundExceptionIsThrown() {

        //when
        when(recipeRepository.findById(anyString())).thenReturn(Optional.empty());

        ingredientService.findByRecipeIdAndIngredientId("rec1", "ingr1");

        // then exception is thrown
    }

    @Test(expected = NotFoundException.class)
    public void whenFindByRecipeIdAndIngredientIdIngredientNotFoundThenNotFoundException() {

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

        Optional<Recipe> recipeOptional = Optional.of(recipe);


        //when
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Ingredient foundIngredient = ingredientService.findByRecipeIdAndIngredientId("recipe1", "nothing");

        //then
        // exception is thrown as Ingredient is not matched/found
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId("ingr1");
        ingredient.setRecipeId("rec1");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("ingr1");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient);

        //then
        assertEquals("ingr1", savedIngredient.getId());
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
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById("rec2", "ingr23");

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

}
