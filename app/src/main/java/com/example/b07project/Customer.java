package com.example.b07project;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User{
    public String fullName;
    public ArrayList<String> joinedEvents; //for communicating with Firebase
    public ArrayList<String> scheduledEvents; //for communicating with Firebase
    //private ArrayList<Event> joinedEventsEvent;
    //private ArrayList<Event> scheduledEventsEvent;


    //New customer customer with no joined or scheduled events
    public Customer(String username, String password, String fullName){
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.joinedEvents = new ArrayList<String>();
        this.scheduledEvents = new ArrayList<String>();
    }

    //Create a customer from Firebase
    public Customer(String username, String password, String fullName, ArrayList<String> joinedEvents, ArrayList<String> scheduledEvents){
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.joinedEvents = joinedEvents;
        this.scheduledEvents = scheduledEvents;
    }

    //get all events that a customer is signed up for
    public ArrayList<Event> fetchJoinedEvents(){
        ArrayList<Event> res = new ArrayList<Event>();
        for (Event e: User.fetchAllEvents()){
            if (joinedEvents.contains(e.eventID)){
                res.add(e);
            }
        }
        return res;
    }

    //get all events that a customer has scheduled
    public ArrayList<Event> fetchScheduledEvents(){
        ArrayList<Event> res = new ArrayList<Event>();
        for (Event e: User.fetchAllEvents()){
            if (scheduledEvents.contains(e.eventID)){
                res.add(e);
            }
        }
        return res;
    }

    public String fetchName(){
        return this.fullName;
    }
    public void setName(String newFullName){
        this.fullName = newFullName;
    }
}
