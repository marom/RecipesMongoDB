package com.marom.recipemongo.repositories.reactive;

import com.marom.recipemongo.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {


    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void recipeSave() {

        Recipe recipe = Recipe.builder().directions("Yummy").build();

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }
}
