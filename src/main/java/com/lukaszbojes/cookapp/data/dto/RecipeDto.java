package com.lukaszbojes.cookapp.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RecipeDto {

    private String name;
    private String description;
    private String image;
    private String source;

    private List<IngredientDto> ingredients;

    public RecipeDto() {
    }

    public RecipeDto(String name, String description, String image, String source, List<IngredientDto> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.image = image;
        this.source = source;
    }
}
