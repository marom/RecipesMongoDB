package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.dto.RecipeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeDtoToRecipe implements Converter<RecipeDto, Recipe> {

    private IngredientDtoToIngredient toIngredient;
    private NotesDtoToNotes toNotes;
    private CategoryDtoToCategory toCategory;

    public RecipeDtoToRecipe(IngredientDtoToIngredient toIngredient, NotesDtoToNotes toNotes, CategoryDtoToCategory toCategory) {
        this.toIngredient = toIngredient;
        this.toNotes = toNotes;
        this.toCategory = toCategory;
    }

    @Override
    public Recipe convert(RecipeDto source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setImage(source.getImage());
        recipe.setDifficulty(source.getDifficulty());

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            convertIngredients(source, recipe);
        }

        recipe.setNotes(toNotes.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            convertCategories(source, recipe);
        }
        return recipe;
    }

    private void convertCategories(RecipeDto source, Recipe recipe) {
        source.getCategories().forEach(categoryDto -> recipe.getCategories().add(toCategory.convert(categoryDto)));
    }

    private void convertIngredients(RecipeDto source, Recipe recipe) {
        source.getIngredients().forEach(ingredientDto -> recipe.getIngredients().add(toIngredient.convert(ingredientDto)));
    }
}
