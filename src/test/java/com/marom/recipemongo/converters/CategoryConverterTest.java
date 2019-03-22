package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.dto.CategoryDto;
import com.marom.recipemongo.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryConverterTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryToCategoryDto categoryToCategoryDto;

    private CategoryConverter categoryConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryConverter = new CategoryConverter(categoryService, categoryToCategoryDto);
    }

    @Test
    public void whenConvertRecipeIdToCategoryDtoThenCorrect() {

        //given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId("cat1");
        categoryDto.setDescription("description");

        Category category = Category.builder().id("cat1").description("description").build();

        //when
        when(categoryService.findById(anyString())).thenReturn(category);
        when((categoryToCategoryDto.convert(category))).thenReturn(categoryDto);

        CategoryDto retCategoryDto = categoryConverter.convert("cat1");

        //then
        assertEquals(retCategoryDto.getId(), category.getId());
        assertEquals(retCategoryDto.getDescription(), category.getDescription());
    }

    @Test
    public void whenConvertNullCategoryIdThenExpectNull(){

        assertNull(categoryConverter.convert(null));
    }
}
