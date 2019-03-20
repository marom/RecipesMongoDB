package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.*;
import com.marom.recipemongo.dto.RecipeDto;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class RecipeToRecipeDtoTest {

    private RecipeToRecipeDto recipeToRecipeDto;
    private CategoryToCategoryDto categoryConverter;
    private IngredientToIngredientDto ingredientConverter;
    private NotesToNotesDto notesConverter;


    @Before
    public void setUp() {
        categoryConverter = new CategoryToCategoryDto();
        ingredientConverter = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
        notesConverter = new NotesToNotesDto();
        recipeToRecipeDto = new RecipeToRecipeDto(categoryConverter, ingredientConverter, notesConverter);
    }

    @Test
    public void whenConvertRecipeToDtoThenCorrect() {

        // given
        final Recipe recipe = Recipe.builder()
                .id("123ws")
                .description("Recipe Description")
                .prepTime(4)
                .cookTime(65)
                .servings(3)
                .source("Recipe source")
                .url("www.url.com")
                .directions("one two three")
                .ingredients(new HashSet<>(Arrays.asList(Ingredient.builder().id("34er").build())))
                .image(ArrayUtils.toObject("Any String to bytes".getBytes()))
                .difficulty(Difficulty.EASY)
                .notes(Notes.builder().id("54rt").build())
                .categories(new HashSet<>(Arrays.asList(Category.builder().id("cat1").build())))
                .build();

        // when
        final RecipeDto recipeDto = recipeToRecipeDto.convert(recipe);

        // then
        assertEquals(recipeDto.getId(), recipe.getId());
        assertEquals(recipeDto.getDescription(), recipe.getDescription());
        assertEquals(recipeDto.getPrepTime(), recipe.getPrepTime());
        assertEquals(recipeDto.getCookTime(), recipe.getCookTime());
        assertEquals(recipeDto.getServings(), recipe.getServings());
        assertEquals(recipeDto.getSource(), recipe.getSource());
        assertEquals(recipeDto.getUrl(), recipe.getUrl());
        assertEquals(recipeDto.getDirections(), recipe.getDirections());
        assertEquals(recipeDto.getIngredients().iterator().next().getId(), ingredientConverter.convert(recipe.getIngredients().iterator().next()).getId());
        assertArrayEquals(recipeDto.getImage(), recipe.getImage());
        assertEquals(recipeDto.getDifficulty(), recipe.getDifficulty());
        assertEquals(recipeDto.getNotes().getId(), notesConverter.convert(recipe.getNotes()).getId());
        assertEquals(recipeDto.getCategories().iterator().next().getId(), categoryConverter.convert(recipe.getCategories().iterator().next()).getId());
    }

    @Test
    public void whenRecipeIsNullThenExpectNull() {

        assertNull(recipeToRecipeDto.convert(null));
    }
}
