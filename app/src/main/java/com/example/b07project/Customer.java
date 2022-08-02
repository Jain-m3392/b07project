package com.example.b07project;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User{
    String fullName;
    ArrayList<Event> joinedEvents;
    ArrayList<Event> scheduledEvents;

    //New customer should be declared after signup/signin
    public Customer(String username, String fullName, String email, String password){
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        //TODO: Load customer-specific event data
    }

    public String fetchName(){
        return this.fullName;
    }

    public void setName(String newFullName){
        this.fullName = newFullName;
    }
}
