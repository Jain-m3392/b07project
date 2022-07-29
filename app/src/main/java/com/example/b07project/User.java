package com.example.b07project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import androidx.annotation.NonNull;

//Base class to communicate with database that can be inherited by both Admin and Customer
public abstract class User {
    String username;
    static ArrayList<Event> allEvents = new ArrayList<Event>();
    static ArrayList<Venue> allVenues = new ArrayList<Venue>();

    static ArrayList<Event> fetchAllEvents(){
        return allEvents;
    }

    static ArrayList<Venue> fetchAllVenues(){
        return allVenues;
    }

    //Initialize data connection
    static void initialize(){

        //Initialise synchronization between allEvents and Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance("https://b07project-696e9-default-rtdb.firebaseio.com/").getReference("events");
        ArrayList<Event> events = new ArrayList<Event>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Event event = child.getValue(Event.class);
                    System.out.println(event);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
