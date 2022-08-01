package com.example.b07project;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Event {

    public String creator;
    public String endTime;
    public int eventID;
    //private ArrayList<User> userCustomers;
    public ArrayList<String> customers;
    public String startTime;
    public int venueID;
    public String capacity;
    public String name;


    public Event(){} //No argument constructor for Firebase to work

    //TODO: Check what type we want each field to be and initialize an Event

    public Event(String creator, String startTime, String endTime, int eventID, int venueID, String capacity, ArrayList<String> customers, String name){
        this.creator = creator;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventID = eventID;
        this.venueID = venueID;
        this.capacity = capacity;
        this.customers = customers;
        this.name = name;
    }

    public void addCustomer(@NonNull User player){

        customers.add(player.username);
        //TODO: sync changes to database
    }


    //fetch all customers signed up for this event
    public ArrayList<User> fetchCustomers(){
        ArrayList<User> res = new ArrayList<User>();
        for (User u : User.fetchAllCustomers()){
            if (this.customers.contains(u.username))
                res.add(u);
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
