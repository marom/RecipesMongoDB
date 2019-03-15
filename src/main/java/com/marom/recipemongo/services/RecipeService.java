package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipes();
}
