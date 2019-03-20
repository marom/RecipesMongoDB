package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.dto.UnitOfMeasureDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureDto implements Converter<UnitOfMeasure, UnitOfMeasureDto> {

    @Override
    public UnitOfMeasureDto convert(UnitOfMeasure source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(source.getId());
        unitOfMeasureDto.setDescription(source.getDescription());
        return unitOfMeasureDto;
    }
}
