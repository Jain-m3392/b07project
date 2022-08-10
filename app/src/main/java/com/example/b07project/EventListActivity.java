package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import java.text.ParseException;
import java.util.ArrayList;


public class EventListActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private ArrayList<Event> allEvents;
    private ArrayList<Venue> allVenues;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        RecyclerView eventsRecyclerView = findViewById(R.id.recyclerview);
        
        //get event info
        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");
        try {
            allEvents = customer.fetchUpcomingEvents();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        allVenues = customer.fetchAllVenues();

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_events);
        nav.setOnItemSelectedListener(this);

        EventsRecyclerAdapter adapter = new EventsRecyclerAdapter(allEvents, allVenues, customer);
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }
}