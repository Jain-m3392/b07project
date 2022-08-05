package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User implements Parcelable, Pushable {
    public String fullName; //Nice display name shown to other users (e.g. John Doe). Maybe optional?
//    public String email;
    public ArrayList<String> joinedEvents; //for communicating with Firebase
    public ArrayList<String> scheduledEvents; //for communicating with Firebase

    //Firebase requires a no-argument constructor or crashes
    public Customer(){}

    //New customer customer with no joined or scheduled events
    //deleted same signature contructor here

    //New customer should be declared after signup/signin
    public Customer(String username, String fullName, String email, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        //TODO: Load customer-specific event data
        this.joinedEvents = new ArrayList<String>();
        this.scheduledEvents = new ArrayList<String>();
        Log.d("User", "ME!");
    }

    //needs to reflect email(?)
    //Create a customer from Firebase
    public Customer(String username, String password, String fullName, ArrayList<String> joinedEvents, ArrayList<String> scheduledEvents){
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.joinedEvents = joinedEvents;
        this.scheduledEvents = scheduledEvents;

    }

    //Allow Parcelable
    public Customer(Parcel in){
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.fullName = in.readString();
        this.joinedEvents = in.readArrayList(null);
        this.scheduledEvents = in.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeList(joinedEvents);
        dest.writeList(scheduledEvents);
    }

    public static Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel parcel) {
            return new Customer(parcel);
        }

        @Override
        public Customer[] newArray(int i) {
            return new Customer[i];
        }
    };


    //get all events that a customer is signed up for
    public ArrayList<Event> fetchJoinedEvents(){
        ArrayList<Event> res = new ArrayList<Event>();
        for (Event e: User.fetchAllEvents()){
            if (joinedEvents.contains(String.valueOf(e.eventID))){
                res.add(e);
            }
        }
        return res;
    }

    //get all events that a customer has scheduled
    public ArrayList<Event> fetchScheduledEvents(){
        ArrayList<Event> res = new ArrayList<Event>();
        for (Event e: User.fetchAllEvents()){
            if (scheduledEvents.contains(String.valueOf(e.eventID))){
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

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof User)){
            return false;
        }
        User c = (User)o;
        return c.username.equals(this.username);
    }

    @Override
    public void push() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers");
        ref.child(username).setValue(this);
    }
}




//    protected Customer(Parcel in) {
//        fullName = in.readString();
//        joinedEvents = in.createStringArrayList();
//        scheduledEvents = in.createStringArrayList();
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(fullName);
//        dest.writeStringList(joinedEvents);
//        dest.writeStringList(scheduledEvents);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
//        @Override
//        public Customer createFromParcel(Parcel in) {
//            return new Customer(in);
//        }
//
//        @Override
//        public Customer[] newArray(int size) {
//            return new Customer[size];
//        }
//    };
