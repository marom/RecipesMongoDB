package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getAllRecipes() {

        Set<Recipe> allRecipes = new HashSet<>();
        recipeRepository.findAll().forEach(allRecipes::add);
        return allRecipes;
    }
}
