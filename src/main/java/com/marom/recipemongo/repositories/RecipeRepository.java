package com.marom.recipemongo.repositories;

import com.marom.recipemongo.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
