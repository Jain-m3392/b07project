package com.example.b07project;

import android.util.Log;

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
    public String username;
    public String password; //Hashed password

    private static ArrayList<Event> allEvents = new ArrayList<Event>();
    private static ArrayList<Venue> allVenues = new ArrayList<Venue>();
    private static ArrayList<User> allCustomers = new ArrayList<User>();
    private static ArrayList<User> allAdmins = new ArrayList<User>();


    public static ArrayList<Event> fetchAllEvents(){
        return allEvents;
    }

    public static ArrayList<Venue> fetchAllVenues(){
        return allVenues;
    }

    public static ArrayList<User> fetchAllCustomers(){
        return allCustomers;
    }

    public static ArrayList<User> fetchAllAdmins(){
        return allAdmins;
    }

    //Initialize data connection
    public static void initialize(){

        //Initialise Firebase connection
        FirebaseDatabase fire = FirebaseDatabase.getInstance("https://b07project-696e9-default-rtdb.firebaseio.com/");
        DatabaseReference ref;

        //Create test values
        Log.d("test", "initializing database");
//        Customer testCustomer = new Customer("test", "test", "Test 1");
//        ArrayList<String> tempList = new ArrayList<String>();
//        tempList.add(testCustomer.username);
//        Event testEvent = new Event("test", "test", "test", "0", "0", 42, tempList);
//
//        ref = fire.getReference();
//        ref.child("customers").child(testCustomer.username).setValue(testCustomer);
//        ref.child("events").child(testEvent.eventID).setValue(testEvent);

        //Initialise synchronization between allCustomers and Firebase
        ref = fire.getReference("customers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Customer customer = child.getValue(Customer.class);
                    if (!allCustomers.contains(customer)){
                        allCustomers.add(customer);
                    }
                    Log.d("test", customer.username);
                    Log.d("test", customer.fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });

        //Initialise synchronization between allAdmins and Firebase
        ref = fire.getReference("admins");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Admin admin = child.getValue(Admin.class);
                    if (!allAdmins.contains(admin)){
                        allAdmins.add(admin);
                    }
                    Log.d("test", admin.username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });


        //Initialise synchronization between allEvents and Firebase
        ref = fire.getReference("events");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Event event = child.getValue(Event.class);
                    if (!allEvents.contains(event)){
                        allEvents.add(event);
                    }
                    Log.d("test", event.eventID);
                    Log.d("test",event.customers.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test",error.toString());
            }
        });

        //Initialise synchronization between allVenues and Firebase
        ref = fire.getReference("venues");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Venue venue = child.getValue(Venue.class);
                    if (!allVenues.contains(venue)){
                        allVenues.add(venue);
                    }
                    Log.d("test", venue.venueID);
                    Log.d("test",venue.events.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test",error.toString());
            }
        });
    }


}
