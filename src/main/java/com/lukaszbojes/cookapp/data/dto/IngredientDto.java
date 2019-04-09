package com.lukaszbojes.cookapp.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientDto {

    private String name;
    private String amount;

    public IngredientDto() {
    }

    public IngredientDto(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }
}
