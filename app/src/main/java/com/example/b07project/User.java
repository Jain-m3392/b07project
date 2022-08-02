package com.example.b07project;

import java.util.ArrayList;
import java.util.LinkedHashSet;

//Base class that can be inherited by both Admin and Customer
public abstract class User {
    String username;
    String email;
    String password;


    //TODO
    //Talks directly to database to fetch events
    ArrayList<Event> fetchAllEvents(){
        return new ArrayList<Event>();
    }

    //TODO
    ArrayList<Venue> fetchAllVenues(){
        return new ArrayList<Venue>();
    }

}
