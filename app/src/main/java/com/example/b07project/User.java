package com.example.b07project;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Base class to communicate with database that can be inherited by both Admin and Customer
public abstract class User {

    public String username;
    public String email;
    public String password; //Hashed password

    private static boolean initialized = false;

    private static ArrayList<Event> allEvents = new ArrayList<Event>();
    private static ArrayList<Venue> allVenues = new ArrayList<Venue>();
    private static ArrayList<User> allCustomers = new ArrayList<User>();
    private static ArrayList<User> allAdmins = new ArrayList<User>();


    //Keeps connection to instance of firebase
    private static FirebaseDatabase fire;


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

    public static FirebaseDatabase fetchFirebase() { return fire; }

    //Initialize data connection
    public static void initialize(){
        if (initialized){
            return;
        }
        initialized = true;

        //Initialise Firebase connection
        fire = FirebaseDatabase.getInstance("https://b07project-696e9-default-rtdb.firebaseio.com/");
        DatabaseReference ref;

        //Create test values
        Log.d("test", "initializing database");

        //Initialise synchronization between allCustomers and Firebase
        ref = fire.getReference("customers");
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey){
                Customer customer = dataSnapshot.getValue(Customer.class);
                if (!allCustomers.contains(customer)){
                    if (customer.joinedEvents == null)
                        customer.joinedEvents = new ArrayList<String>();
                    if (customer.scheduledEvents == null)
                        customer.scheduledEvents = new ArrayList<String>();
                    allCustomers.add(customer);
                }
                Log.d("test", "Found customer: " + customer.username);
//                Log.d("test", customer.fullName);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                if (customer.joinedEvents == null)
                    customer.joinedEvents = new ArrayList<String>();
                if (customer.scheduledEvents == null)
                    customer.scheduledEvents = new ArrayList<String>();

                for (User c : User.fetchAllCustomers()) {
                    if (c.username == customer.username) {
                        Customer cu = (Customer)c;
                        c.password = customer.password;
                        cu.fullName = customer.fullName;
                        cu.joinedEvents = customer.joinedEvents;
                        cu.scheduledEvents = customer.scheduledEvents;
                        break;
                    }
                }
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                allCustomers.remove(customer);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });

        //Initialise synchronization between allAdmins and Firebase
        ref = fire.getReference("admins");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey){
                Admin admin = dataSnapshot.getValue(Admin.class);
                if (admin.venues == null)
                    admin.venues  = new ArrayList<Integer>();

                if (!allAdmins.contains(admin)){
                    allAdmins.add(admin);
                }
                Log.d("test", "Found admin: " + admin.venues);
//                Log.d("test", customer.fullName);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                if (admin.venues == null)
                    admin.venues  = new ArrayList<Integer>();

                for (User a : User.fetchAllAdmins()) {
                    if (a.username.equals(admin.username)) {
                        Admin ac = (Admin)a;
                        ac.venues = admin.venues;
                        ac.password = admin.password;
                        ac.email = admin.email;
                        break;
                    }
                }
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                allAdmins.remove(admin);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });


        //Initialise synchronization between allEvents and Firebase
        ref = fire.getReference("events");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey){
                Event event = dataSnapshot.getValue(Event.class);
                if (event.customers == null)
                    event.customers = new ArrayList<String>();

                if (!allEvents.contains(event)){
                    allEvents.add(event);
                }
                Log.d("test", "Found event: " + event.eventID);
//                Log.d("test", customer.fullName);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event.customers == null)
                    event.customers = new ArrayList<String>();

                for (Event e : User.fetchAllEvents()) {
                    if (e.eventID == event.eventID) {
                        e.startTime = event.startTime;
                        e.venueID = event.venueID;
                        e.endTime = event.endTime;
                        e.capacity = event.capacity;
                        e.creator = event.creator;
                        e.customers = event.customers;
                        e.date = event.date;
                        break;
                    }
                }
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                allEvents.remove(event);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });

        //Initialise synchronization between allVenues and Firebase
        ref = fire.getReference("venues");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey){
                Venue venue = dataSnapshot.getValue(Venue.class);
                if (venue.events == null)
                    venue.events = new ArrayList<Integer>();
                if (venue.accessibleSports == null)
                    venue.accessibleSports = new ArrayList<String>();
                if (!allVenues.contains(venue)){
                    allVenues.add(venue);
                }
                Log.d("test", "Found venue: " + venue.venueName);
//                Log.d("test", customer.fullName);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Venue venue = dataSnapshot.getValue(Venue.class);
                if (venue.events == null)
                    venue.events = new ArrayList<Integer>();
                if (venue.accessibleSports == null)
                    venue.accessibleSports = new ArrayList<String>();

                for (Venue v : User.fetchAllVenues()) {
                    if (v.venueID == venue.venueID) {
                        v.venueName = venue.venueName;
                        v.venueLocation = venue.venueLocation;
                        v.events = venue.events;
                        v.accessibleSports = venue.accessibleSports;
                        break;
                    }
                }
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Venue venue = dataSnapshot.getValue(Venue.class);
                allVenues.remove(venue);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });
    }



}
