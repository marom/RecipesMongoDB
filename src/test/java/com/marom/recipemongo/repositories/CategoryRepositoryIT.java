package com.marom.recipemongo.repositories;

import com.marom.recipemongo.bootstrap.RecipeBootstrap;
import com.marom.recipemongo.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryRepositoryIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Before
    public void setUp() {

        categoryRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        recipeRepository.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, unitOfMeasureRepository, recipeRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    public void findByDescription() {

        assertThat(categoryRepository.findByDescription("Italian").get().getDescription(), is("Italian"));
    }

    @Test
    public  void  findByDescriptionNotFound() {

        assertFalse(categoryRepository.findByDescription("NotExist").isPresent());
    }
}
