package com.marom.recipemongo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDto {

    private String id;
    private String recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureDto uom;
}
