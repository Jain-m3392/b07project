package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

//for displaying the events happening at a particular venue
//has a "create an event" button which takes you to the customerScheduleEvent activity
public class customerCreateEvents extends AppCompatActivity {
    ListView listview;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_events);
        Intent intent = getIntent();
        String venueName = intent.getExtras().getString("Venue");
        Venue venue = null;
        for(Venue v: User.fetchAllVenues()){
            if(v.venueName==venueName)
                venue = v;
        }
        ArrayList<Event> eventList = venue.fetchEvents();
        listview = (ListView) findViewById(R.id.eventList);
        EventAdapter EventAdapter = new EventAdapter(this, R.layout.eventlist, eventList);
        listview.setAdapter(EventAdapter);
        button = findViewById(R.id.scheduleEvent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerCreateEvents.this, customerScheduleEvent.class);
                startActivity(intent);
            }
        });

    }
}