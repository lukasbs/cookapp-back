package com.lukaszbojes.cookapp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long favouriteId;

    @ManyToOne
    @JoinColumn(name="userID")
    private User user;

    @ManyToOne
    @JoinColumn(name="recipeID")
    private Recipe recipe;

    public Favourite() {
    }

    public Favourite(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }
}
