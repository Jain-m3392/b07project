package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    //get upcoming events
    public ArrayList<Event> fetchUpcomingEvents() throws ParseException {
        ArrayList<Event> res = new ArrayList<Event>();
        DateFormat fbDate = new SimpleDateFormat("MM/dd/yyyy");
        for (Event e: User.fetchAllEvents()){
            Date eventDate = fbDate.parse(e.date);
            //get current date
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0); calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0); calendar.set(Calendar.MILLISECOND, 0);
            Date currentDate = calendar.getTime();
            if (eventDate.compareTo(currentDate) >= 0) {
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

    public void addEvent(String s){
        this.scheduledEvents.add(s);
    }

}
