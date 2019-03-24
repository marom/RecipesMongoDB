package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeReactiveRepository recipeRepository;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Flux<Recipe> getAllRecipes() {

        return  recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {

        return recipeRepository.findById(id);
    }

    @Override
    public Mono<Recipe> saveRecipe(Recipe recipe) {

        return recipeRepository.save(recipe);
    }

    @Override
    public Mono<Void> deleteById(String recipeId) {

        recipeRepository.deleteById(recipeId).block();
        return Mono.empty();
    }
}
