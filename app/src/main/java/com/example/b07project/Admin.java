package com.example.b07project;

import java.util.ArrayList;

public class Admin extends User{

    ArrayList<Venue> createdVenues;

    public Admin(String username){
        this.username = username;
    }
}
