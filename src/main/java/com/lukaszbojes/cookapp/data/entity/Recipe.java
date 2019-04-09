package com.lukaszbojes.cookapp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long recipeID;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "recipe")
    private List<Favourite> favourites;

//    @ManyToMany()
//    @JoinTable(name = "User_Recipe",
//            joinColumns = {
//                @JoinColumn(name = "recipeID")
//            },
//            inverseJoinColumns = {
//                @JoinColumn(name = "userID")
//    })
//    private List<User> users;

    public Recipe() {
    }

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recipe(String name, String description, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }
}
