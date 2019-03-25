package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.repositories.reactive.RecipeReactiveRepository;
import com.marom.recipemongo.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {


    private RecipeReactiveRepository recipeRepository;
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeReactiveRepository recipeRepository, UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public Mono<Ingredient> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        return recipeRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .map(ingredient -> {
                    ingredient.setRecipeId(recipeId);
                    return ingredient;
                })
                .single();
    }

    @Override
    public Mono<Ingredient> saveIngredient(Ingredient ingredient) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredient.getRecipeId()).blockOptional();

        if(!recipeOptional.isPresent()){
            throw  new NotFoundException("Recipe not found for id: " + ingredient.getRecipeId());
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingr -> ingr.getId().equals(ingredient.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){

                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredient.getDescription());
                ingredientFound.setAmount(ingredient.getAmount());
                ingredientFound.setRecipeId(ingredient.getRecipeId());

                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredient.getUom().getId()).block());

                if (ingredientFound.getUom() == null) {
                    new RuntimeException("UOM NOT FOUND");
                }
            } else {
                //add new Ingredient
                ingredient.setId(UUID.randomUUID().toString());
                ingredient.setRecipeId(recipe.getId());
                ingredient.setUom(unitOfMeasureRepository
                        .findById(ingredient.getUom().getId()).block());
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredient.getId()))
                    .findFirst();

             //to do check for fail
            return Mono.just(savedIngredientOptional.get());
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String ingredientToDeleteId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId).blockOptional();

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientToDeleteId))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");

                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
        return Mono.empty();
    }
}
