package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.dto.IngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientDto implements Converter<Ingredient, IngredientDto> {

    private UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    public IngredientToIngredientDto(UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto) {
        this.unitOfMeasureToUnitOfMeasureDto = unitOfMeasureToUnitOfMeasureDto;
    }

    @Override
    public IngredientDto convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(source.getId());
        ingredientDto.setAmount(source.getAmount());
        ingredientDto.setDescription(source.getDescription());
        ingredientDto.setUom(unitOfMeasureToUnitOfMeasureDto.convert(source.getUom()));
        return ingredientDto;
    }
}
