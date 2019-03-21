package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.dto.UnitOfMeasureDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureDtoToUnitOfMeasureTest {

    private UnitOfMeasureDtoToUnitOfMeasure unitOfMeasureConverter;

    @Before
    public void setUp() {
        unitOfMeasureConverter = new UnitOfMeasureDtoToUnitOfMeasure();
    }

    @Test
    public void whenConvertToUnitOfMeasureThenCorrect() {

        //given
        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId("uomDto1");
        unitOfMeasureDto.setDescription("Uom Dto description");

        //when
        final UnitOfMeasure unitOfMeasure = unitOfMeasureConverter.convert(unitOfMeasureDto);

        //then
        assertEquals(unitOfMeasure.getId(), unitOfMeasureDto.getId());
        assertEquals(unitOfMeasure.getDescription(), unitOfMeasureDto.getDescription());
    }

    @Test
    public void whenConvertNullUomDtoThenExpectNull() {

        assertNull(unitOfMeasureConverter.convert(null));
    }
}
