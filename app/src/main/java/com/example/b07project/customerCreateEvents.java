package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

//for displaying the events happening at a particular venue
//has a "create an event" button which takes you to the customerScheduleEvent activity
public class customerCreateEvents extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    ListView listview;
    private Button button;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_events);
        Intent intent = getIntent();
        //from the previous page (login page)
        customer = intent.getParcelableExtra("Customer");
        String venueName = intent.getExtras().getString("Venue");

        //set up navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_venues);
        nav.setOnItemSelectedListener(this);

        Venue venue = null;
        for(Venue v: User.fetchAllVenues()){
            if(v.venueName.equals(venueName))
                venue = v;
        }
        ArrayList<Event> eventList = venue.fetchEvents();
        listview = (ListView) findViewById(R.id.eventList);
        EventAdapter EventAdapter = new EventAdapter(this, R.layout.eventlist, eventList);
        listview.setAdapter(EventAdapter);
        button = findViewById(R.id.scheduleEvent);
        Venue finalVenue = venue;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerCreateEvents.this, customerScheduleEvent.class);
                intent.putExtra("Venue", finalVenue);
                intent.putExtra("Customer", customer);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }
}