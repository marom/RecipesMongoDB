package com.marom.recipemongo.repositories.reactive;

import com.marom.recipemongo.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryRepository;


    @Before
    public void setUp() {
        categoryRepository.deleteAll().block();
    }

    @Test
    public void testSave() {
        Category category = new Category();
        category.setDescription("Foo");

        categoryRepository.save(category).block();

        Long count = categoryRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription("Foo");

        categoryRepository.save(category).then().block();

        Category fetchedCat = categoryRepository.findByDescription("Foo").block();

        assertNotNull(fetchedCat.getId());
    }

}
