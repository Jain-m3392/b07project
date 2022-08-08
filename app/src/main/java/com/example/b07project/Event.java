package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class Event implements Pushable, Parcelable {

    public String creator;
    public String endTime;
    public int eventID;
    //private ArrayList<User> userCustomers;
    public ArrayList<String> customers;
    public String startTime;
    public int venueID;
    public int capacity;
    public String name;
    public String sportsType;
    public String date;


    public Event(){} //No argument constructor for Firebase to work

    //TODO: Check what type we want each field to be and initialize an Event

    public Event(String creator, String startTime, String endTime, int eventID, int venueID, int capacity, ArrayList<String> customers, String name, String sportsType, String date){
        this.creator = creator;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventID = eventID;
        this.venueID = venueID;
        this.capacity = capacity;
        this.customers = customers;
        this.name = name;
        this.sportsType = sportsType;
        this.date = date;
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

    public int getCapacity() {
        return capacity;
    }

    public String getStringCapacity() { return String.valueOf(capacity); }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getVenueID() { return venueID; }


    //
    @Override
    public void push() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        ref.child(String.valueOf(this.eventID)).setValue(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Event (Parcel in) {
        this.creator = in.readString();
        this.name = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.capacity = in.readInt();
        this.eventID = in.readInt();
        this.customers = in.readArrayList(null);
        this.date = in.readString();
        this.sportsType = in.readString();
        this.venueID = in.readInt();
    }

    public static Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(creator);
        parcel.writeString(name);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeInt(capacity);
        parcel.writeInt(eventID);
        parcel.writeList(customers);
        parcel.writeString(date);
        parcel.writeString(sportsType);
        parcel.writeInt(venueID);
    }
}
