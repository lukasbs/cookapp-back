package com.lukaszbojes.cookapp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class FridgeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fridgeID;

    private String name;
    private String amount;
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name="userID")
    private User user;

    public FridgeItem() {
    }

    public FridgeItem(String name, String amount, Date expirationDate, User user) {
        this.name = name;
        this.amount = amount;
        this.expirationDate = expirationDate;
        this.user = user;
    }
}
