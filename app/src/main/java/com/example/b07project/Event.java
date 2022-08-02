package com.example.b07project;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Event {

    public String creator;
    public String endTime;
    public String eventID;
    //private ArrayList<User> userCustomers;
    public ArrayList<String> customers;
    public String startTime;
    public String venueID;
    public int capacity;
    public String name;


    public Event(){} //No argument constructor for Firebase to work

    //TODO: Check what type we want each field to be and initialize an Event

    public Event(String creator, String startTime, String endTime, String eventID, String venueID, int capacity, ArrayList<String> customers){
        this.creator = creator;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventID = eventID;
        this.venueID = venueID;
        this.capacity = capacity;
        this.customers = customers;


    }

    public void addCustomer(User player){

        customers.add(player.username);
        //TODO: sync changes to database
    }

    //fetch all customers signed up for this event
    public ArrayList<User> fetchCustomers(){
        ArrayList<User> res = new ArrayList<User>();
        for (User u : User.fetchAllCustomers()){
            if (this.customers.contains(u.username)){
                res.add(u);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof Event)){
            return false;
        }
        Event e = (Event)o;
        return e.eventID == this.eventID;
    }

}
