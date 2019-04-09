package com.lukaszbojes.cookapp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long shoppingListID;

    private String name;
    private String amount;

    @ManyToOne
    @JoinColumn(name="userID")
    private User user;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String name, String amount, User user) {
        this.name = name;
        this.amount = amount;
        this.user = user;
    }
}
