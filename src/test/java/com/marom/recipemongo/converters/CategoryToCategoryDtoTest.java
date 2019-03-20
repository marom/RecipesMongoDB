package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.dto.CategoryDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CategoryToCategoryDtoTest {

    private CategoryToCategoryDto categoryToCategoryDto;

    @Before
    public void setUp() {
        categoryToCategoryDto = new CategoryToCategoryDto();
    }

    @Test
    public void whenConvertCategoryToCategoryDtoThenCorrect() {

        // given
        final Category category = new Category();
        category.setId("qwe43");
        category.setDescription("Category Description");

        // when
        final CategoryDto categoryDto = categoryToCategoryDto.convert(category);

        // then
        assertEquals(categoryDto.getId(), category.getId());
        assertEquals(categoryDto.getDescription(), category.getDescription());
    }

    @Test
    public void whenConvertNullCategoryThenExpectNull() {

        assertNull(categoryToCategoryDto.convert(null));
    }
}
