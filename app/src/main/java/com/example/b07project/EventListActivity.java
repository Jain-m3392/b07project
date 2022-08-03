package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class EventListActivity extends AppCompatActivity {
    private ArrayList<Event> allEvents;
    private ArrayList<Venue> allVenues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        RecyclerView eventsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        
        //get event info
        Intent intent = getIntent();
        Customer customer = intent.getParcelableExtra("Customer");
        allEvents = customer.fetchAllEvents();
        allVenues = customer.fetchAllVenues();

        EventsRecyclerAdapter adapter = new EventsRecyclerAdapter(allEvents, allVenues);
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}