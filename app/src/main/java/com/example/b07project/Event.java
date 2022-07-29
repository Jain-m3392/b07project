package com.example.b07project;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Event {

    public String creator;
    public String endTime;
    public String eventID;
    private ArrayList<User> userCustomers;
    public ArrayList<String> customers;
    public String startTime;
    public String venueID;
    public int capacity;


    //TODO: Check what type we want each field to be and initialize an Event
    public Event(String creator, String startTime, String endTime, String eventID, String venueID, int capacity, ArrayList<String> customers){
        this.creator = creator;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventID = eventID;
        this.venueID = venueID;
        this.capacity = capacity;
        this.customers = customers;
        //TODO: find customers with given usernames, and add them to this.userCustomers
    }

    public void addCustomer(User player){

        customers.add(player.username);
        userCustomers.add(player);
    }

    public ArrayList<User> fetchCustomers(){
        return userCustomers;
    }


}
