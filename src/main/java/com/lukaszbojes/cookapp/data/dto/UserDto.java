package com.lukaszbojes.cookapp.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private String name;
    private String password;
    private String role;

    public UserDto() {
    }

    public UserDto(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
