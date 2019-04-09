package com.lukaszbojes.cookapp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;

    private String name;
    private String password;
    private String role;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<ShoppingListItem> shoppingListItems;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<FridgeItem> fridgeItems;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<Favourite> favourites;

//    @ManyToMany()
//    private List<Recipe> recipes;

    public User() {
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

}
