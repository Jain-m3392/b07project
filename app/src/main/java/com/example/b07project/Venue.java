package com.example.b07project;

import java.util.ArrayList;

public class Venue {

    public ArrayList<String> events;
    public String venueID;
    public String venueLocation;
    public String venueName;

    public Venue(){} //Empty constructor for Firebase to work

    public Venue(String venueID, String venueName, String venueLocation, ArrayList<String> events){
        this.events = events;
        this.venueID = venueID;
        this.venueLocation = venueLocation;
        this.venueName = venueName;
    }

    public ArrayList<Event> fetchEvents(){
        ArrayList<Event> res = new ArrayList<Event>();
        for (Event e: User.fetchAllEvents()){
            if (e.venueID == this.venueID){
                res.add(e);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof Venue)){
            return false;
        }
        Venue v = (Venue)o;
        return v.venueID == this.venueID;
    }
}
