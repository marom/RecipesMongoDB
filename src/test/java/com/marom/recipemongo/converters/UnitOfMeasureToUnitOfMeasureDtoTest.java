package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.dto.UnitOfMeasureDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UnitOfMeasureToUnitOfMeasureDtoTest {


    private UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    @Before
    public void setUp() {
        unitOfMeasureToUnitOfMeasureDto = new UnitOfMeasureToUnitOfMeasureDto();
    }

    @Test
    public void convertToDtoCorrect() {

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("12qw");
        unitOfMeasure.setDescription("Uom Description");

        UnitOfMeasureDto unitOfMeasureDto = unitOfMeasureToUnitOfMeasureDto.convert(unitOfMeasure);

        assertEquals(unitOfMeasureDto.getId(), unitOfMeasure.getId());
        assertEquals(unitOfMeasureDto.getDescription(), unitOfMeasure.getDescription());
    }

    @Test
    public void whenUomIsNUllShouldReturnNull() {

       assertNull(unitOfMeasureToUnitOfMeasureDto.convert(null));
    }
}
