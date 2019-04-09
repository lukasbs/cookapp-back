package com.lukaszbojes.cookapp.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShoppingListItemDto {

    private String name;
    private String amount;

    public ShoppingListItemDto() {
    }

    public ShoppingListItemDto(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }
}
