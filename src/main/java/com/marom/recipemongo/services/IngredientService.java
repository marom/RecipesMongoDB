package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<Ingredient> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<Ingredient> saveIngredient(Ingredient ingredient);

    Mono<Void> deleteById(String recipeId, String ingredientId);
}
