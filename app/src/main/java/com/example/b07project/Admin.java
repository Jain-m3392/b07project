package com.example.b07project;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Admin extends User{

    public ArrayList<String> venues;

    public Admin(){} //Empty constructor for Firebase loading
    public Admin(String username, ArrayList<String> venues){
        this.username = username;
        this.venues = venues;
    }

    public ArrayList<Venue> fetchCreatedVenues(){
        ArrayList<Venue> res = new ArrayList<>();
        for (Venue v: User.fetchAllVenues()){
            if (venues.contains(v.venueID)){
                res.add(v);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof User)){
            return false;
        }
        User a = (User)o;
        return a.username == this.username;
    }

}
