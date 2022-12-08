package com.example.servies;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public String id;
    public String username;
    public String email;
    public String password;
    public String phoneNumber;
    public String userRole;

    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String id, String username, String email, String password, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
