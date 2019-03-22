package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Ingredient;

public interface IngredientService {

    Ingredient findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Ingredient saveIngredient(Ingredient ingredient);
}
