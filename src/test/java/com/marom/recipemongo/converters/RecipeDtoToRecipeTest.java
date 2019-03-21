package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.domain.Difficulty;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.dto.CategoryDto;
import com.marom.recipemongo.dto.IngredientDto;
import com.marom.recipemongo.dto.NotesDto;
import com.marom.recipemongo.dto.RecipeDto;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;

public class RecipeDtoToRecipeTest {

    private CategoryDtoToCategory toCategory;
    private NotesDtoToNotes toNotes;
    private IngredientDtoToIngredient toIngredient;
    private RecipeDtoToRecipe toRecipe;
    private RecipeDto recipeDto;

    @Before
    public void setUp() {

        toCategory = new CategoryDtoToCategory();
        toNotes = new NotesDtoToNotes();
        toIngredient = new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure());
        toRecipe = new RecipeDtoToRecipe(toIngredient, toNotes, toCategory);

        //given
        final NotesDto notesDto = new NotesDto();
        notesDto.setId("noteId");
        notesDto.setRecipeNotes("Recipe Notes");

        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId("catId");
        categoryDto.setDescription("Category description");

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId("ingredientId");
        ingredientDto.setDescription("description");
        ingredientDto.setAmount(new BigDecimal("12"));

        recipeDto = new RecipeDto();
        recipeDto.setId("recipeId");
        recipeDto.setDescription("recipe description");
        recipeDto.setPrepTime(3);
        recipeDto.setCookTime(35);
        recipeDto.setServings(3);
        recipeDto.setUrl("www.url.com");
        recipeDto.setSource("source");
        recipeDto.setDirections("some directions");
        recipeDto.setIngredients(new HashSet<>(Arrays.asList(ingredientDto)));
        recipeDto.setImage(ArrayUtils.toObject("Any String to bytes".getBytes()));
        recipeDto.setDifficulty(Difficulty.EASY);
        recipeDto.setNotes(notesDto);
        recipeDto.setCategories(new HashSet<>(Arrays.asList(categoryDto)));

    }

    @Test
    public void whenConvertRecipeDtoToRecipeThenCorrect() {

        //when
        final Recipe recipe = toRecipe.convert(recipeDto);

        //then
        assertEquals(recipe.getId(), recipeDto.getId());
        assertEquals(recipe.getDescription(), recipeDto.getDescription());
        assertEquals(recipe.getPrepTime(), recipeDto.getPrepTime());
        assertEquals(recipe.getCookTime(), recipeDto.getCookTime());
        assertEquals(recipe.getServings(), recipeDto.getServings());
        assertEquals(recipe.getUrl(), recipeDto.getUrl());
        assertEquals(recipe.getSource(), recipeDto.getSource());
        assertEquals(recipe.getDirections(), recipeDto.getDirections());
        assertThat(recipe.getIngredients(), hasSize(1));
        assertArrayEquals(recipe.getImage(), recipeDto.getImage());
        assertEquals(recipe.getDifficulty(), recipeDto.getDifficulty());
        assertEquals(recipe.getNotes().getId(), recipeDto.getNotes().getId());
        assertEquals(recipe.getNotes().getRecipeNotes(), recipeDto.getNotes().getRecipeNotes());
        assertThat(recipe.getCategories(), hasSize(1));
        assertThat(recipe.getCategories(), not(emptyIterableOf(Category.class)));
    }

    @Test
    public void whenConvertNullRecipeThenExpectNull() {
        assertNull(toRecipe.convert(null));
    }

    @Test
    public void whenConvertWithIngredientsNullThenCorrect() {

        //given
        recipeDto.setIngredients(null);

        //when
        final Recipe recipe = toRecipe.convert(recipeDto);

        //then
        assertEquals(recipe.getId(), recipeDto.getId());
        assertThat(recipe.getIngredients(), hasSize(0));
    }

    @Test
    public void whenConvertWithCategoriesNullThenCorrect() {

        //given
        recipeDto.setCategories(null);

        //when
        final Recipe recipe = toRecipe.convert(recipeDto);

        //then
        assertEquals(recipe.getId(), recipeDto.getId());
        assertThat(recipe.getCategories(), hasSize(0));
    }

    @Test
    public void whenConvertWithIngredientsIsEmptyThenCorrect() {

        //given
        recipeDto.setIngredients(new HashSet<>());

        //when
        final Recipe recipe = toRecipe.convert(recipeDto);

        //then
        assertEquals(recipe.getId(), recipeDto.getId());
        assertThat(recipe.getIngredients(), hasSize(0));
    }

    @Test
    public void whenConvertWithCategoriesIsEmptyThenCorrect() {

        //given
        recipeDto.setCategories(new HashSet<>());

        //when
        final Recipe recipe = toRecipe.convert(recipeDto);

        //then
        assertEquals(recipe.getId(), recipeDto.getId());
        assertThat(recipe.getCategories(), hasSize(0));
    }
}
