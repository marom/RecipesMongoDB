package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> getAllRecipes();

    Mono<Recipe> findById(String id);

    Mono<Recipe> saveRecipe(Recipe recipe);

    Mono<Void> deleteById(String recipeId);
}
