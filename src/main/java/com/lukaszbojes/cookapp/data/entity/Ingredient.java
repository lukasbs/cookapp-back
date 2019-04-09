package com.lukaszbojes.cookapp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ingredientID;

    private String name;
    private String amount;

    @ManyToOne
    @JoinColumn(name="recipeID")
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String name, String amount, Recipe recipe) {
        this.name = name;
        this.amount = amount;
        this.recipe = recipe;
    }
}
