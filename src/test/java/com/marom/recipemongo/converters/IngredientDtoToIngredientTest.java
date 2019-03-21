package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.dto.IngredientDto;
import com.marom.recipemongo.dto.UnitOfMeasureDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientDtoToIngredientTest {

    private IngredientDtoToIngredient toIngredientConverter;
    private UnitOfMeasureDtoToUnitOfMeasure toUomConverter;

    @Before
    public void setUp() {
        toUomConverter = new UnitOfMeasureDtoToUnitOfMeasure();
        toIngredientConverter = new IngredientDtoToIngredient(toUomConverter);
    }

    @Test
    public void whenConvertFromDtoThenCorrect() {

        //given
        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId("uomDto");
        unitOfMeasureDto.setDescription("UomDto description");

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("uomDto");
        unitOfMeasure.setDescription("UomDto description");

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId("ingrDto");
        ingredientDto.setAmount(new BigDecimal("12"));
        ingredientDto.setDescription("IngredientDto description");
        ingredientDto.setUom(unitOfMeasureDto);

        //when
        final Ingredient ingredient = toIngredientConverter.convert(ingredientDto);

        //then
        assertEquals(ingredient.getId(), ingredientDto.getId());
        assertEquals(ingredient.getAmount(), ingredientDto.getAmount());
        assertEquals(ingredient.getDescription(), ingredientDto.getDescription());
        assertEquals(ingredient.getUom().getId(), unitOfMeasure.getId());
        assertEquals(ingredient.getUom().getDescription(), unitOfMeasure.getDescription());
    }

    @Test
    public void whenConvertNullDtoThenExpectNull() {

        assertNull(toIngredientConverter.convert(null));
    }
}
