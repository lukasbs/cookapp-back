package com.lukaszbojes.cookapp.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LoginDto {

    private String token;
    private List<RecipeDto> favouriteRecipes;
    private List<FridgeItemDto> fridgeItems;
    private List<ShoppingListItemDto> shoppingListItems;

    public LoginDto(String token, List<RecipeDto> favouriteRecipes, List<FridgeItemDto> fridgeItems, List<ShoppingListItemDto> shoppingListItems) {
        this.token = token;
        this.favouriteRecipes = favouriteRecipes;
        this.fridgeItems = fridgeItems;
        this.shoppingListItems = shoppingListItems;
    }
}
