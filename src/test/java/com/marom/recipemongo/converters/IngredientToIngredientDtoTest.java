package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.dto.IngredientDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientDtoTest {

    private IngredientToIngredientDto ingredientToIngredientDto;
    private UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    @Before
    public void setUp() {
        ingredientToIngredientDto = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
        unitOfMeasureToUnitOfMeasureDto = new UnitOfMeasureToUnitOfMeasureDto();
    }

    @Test
    public void whenConvertIngredientToDtoThenCorrect() {

        // given
        final Ingredient ingredient = new Ingredient();
        ingredient.setId("12ws");
        ingredient.setAmount(new BigDecimal(12.43));
        ingredient.setDescription("Ingredient Description");

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("12qw");
        unitOfMeasure.setDescription("Uom Description");

        ingredient.setUom(unitOfMeasure);

        // when
        IngredientDto ingredientDto = ingredientToIngredientDto.convert(ingredient);

        // then
        assertEquals(ingredientDto.getId(), ingredient.getId());
        assertEquals(ingredientDto.getAmount(), ingredient.getAmount());
        assertEquals(ingredientDto.getDescription(), ingredient.getDescription());
        assertEquals(ingredientDto.getUom().getId(), unitOfMeasureToUnitOfMeasureDto.convert(ingredient.getUom()).getId());
        assertEquals(ingredientDto.getUom().getDescription(), unitOfMeasureToUnitOfMeasureDto.convert(ingredient.getUom()).getDescription());
    }

    @Test
    public void whenConvertNullIngredientThenExpectNull() {

        assertNull(ingredientToIngredientDto.convert(null));
    }
}
