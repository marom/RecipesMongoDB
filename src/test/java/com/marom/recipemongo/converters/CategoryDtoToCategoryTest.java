package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.dto.CategoryDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryDtoToCategoryTest {

    private CategoryDtoToCategory categoryDtoToCategory;

    @Before
    public void setUp() throws Exception {
        categoryDtoToCategory = new CategoryDtoToCategory();
    }

    @Test
    public void whenConvertToCategoryThenCorrect() {

        // given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId("catDto1");
        categoryDto.setDescription("CategoryDto description");

        // when
        final Category category = categoryDtoToCategory.convert(categoryDto);

        // then
        assertEquals(category.getId(), categoryDto.getId());
        assertEquals(category.getDescription(), categoryDto.getDescription());
    }

    @Test
    public void whenConvertNullCategoryDtoThenExpectNull() {

        assertNull(categoryDtoToCategory.convert(null));
    }
}
