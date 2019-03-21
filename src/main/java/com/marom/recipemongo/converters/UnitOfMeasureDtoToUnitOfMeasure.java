package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.dto.UnitOfMeasureDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureDtoToUnitOfMeasure implements Converter<UnitOfMeasureDto, UnitOfMeasure> {

    @Override
    public UnitOfMeasure convert(UnitOfMeasureDto source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());
        return unitOfMeasure;
    }
}
