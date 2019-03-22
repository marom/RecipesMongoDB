package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.dto.CategoryDto;
import com.marom.recipemongo.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryConverter implements Converter<String, CategoryDto> {

    private CategoryService categoryService;
    private CategoryToCategoryDto categoryToCategoryDto;

    public CategoryConverter(CategoryService categoryService, CategoryToCategoryDto categoryToCategoryDto) {
        this.categoryService = categoryService;
        this.categoryToCategoryDto = categoryToCategoryDto;
    }

    @Override
    public CategoryDto convert(String categoryDtoId) {

        if (categoryDtoId == null) {
            return null;
        }

        Category category = categoryService.findById(categoryDtoId);

        return categoryToCategoryDto.convert(category);
    }
}
