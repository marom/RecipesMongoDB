package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.dto.RecipeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeDto implements Converter<Recipe, RecipeDto> {

    private final CategoryToCategoryDto categoryConverter;
    private final IngredientToIngredientDto ingredientConverter;
    private final NotesToNotesDto notesConverter;

    public RecipeToRecipeDto(CategoryToCategoryDto categoryConverter, IngredientToIngredientDto ingredientConverter, NotesToNotesDto notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    public RecipeDto convert(Recipe source) {

        if (source == null) {
            return null;
        }

        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setId(source.getId());
        recipeDto.setCookTime(source.getCookTime());
        recipeDto.setPrepTime(source.getPrepTime());
        recipeDto.setDescription(source.getDescription());
        recipeDto.setDifficulty(source.getDifficulty());
        recipeDto.setDirections(source.getDirections());
        recipeDto.setServings(source.getServings());
        recipeDto.setSource(source.getSource());
        recipeDto.setUrl(source.getUrl());
        recipeDto.setImage(source.getImage());
        recipeDto.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach((Category category) -> recipeDto.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipeDto.getIngredients().add(ingredientConverter.convert(ingredient)));
        }
        return recipeDto;
    }
}
