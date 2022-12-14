package com.example.servies.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
    public String id;
    public String bio;
    public String exp;
    public String country;
    public String skills;
    public String contacts;
    public String links;

    public Profile(String id, String bio, String exp, String country, String skills, String contacts, String links) {
        this.id = id;
        this.bio = bio;
        this.exp = exp;
        this.country = country;
        this.skills = skills;
        this.contacts = contacts;
        this.links = links;
    }
}
