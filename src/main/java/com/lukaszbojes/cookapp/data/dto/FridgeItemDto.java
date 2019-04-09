package com.lukaszbojes.cookapp.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class FridgeItemDto {

    private String name;
    private String amount;
    private Date expirationDate;

    public FridgeItemDto() {
    }

    public FridgeItemDto(String name, String amount, Date expirationDate) {
        this.name = name;
        this.amount = amount;
        this.expirationDate = expirationDate;
    }
}
