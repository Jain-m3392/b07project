package com.example.b07project;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Event {

    String creator;
    String endTime;
    String eventID;
    ArrayList<User> customers;
    String startTime;
    String venueID;


    //TODO: Check what type we want each field to be and initialize an Event
    public Event(String creator, String startTime, String endTime, String eventID, String venueID){

    }

    public void addCustomer(User player){
        customers.add(player);
    }

    public ArrayList<User> fetchCustomers(){
        return customers;
    }
}
