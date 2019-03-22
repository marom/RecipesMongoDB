package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.dto.IngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoToIngredient implements Converter<IngredientDto, Ingredient> {

    private UnitOfMeasureDtoToUnitOfMeasure toUomConverter;

    public IngredientDtoToIngredient(UnitOfMeasureDtoToUnitOfMeasure toUomConverter) {
        this.toUomConverter = toUomConverter;
    }

    @Override
    public Ingredient convert(IngredientDto source) {

        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setRecipeId(source.getRecipeId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(toUomConverter.convert(source.getUom()));
        return ingredient;
    }
}
