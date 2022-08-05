package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class CustomerEventsView extends AppCompatActivity {

    Customer customer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");
        ArrayList<Venue> venues = customer.fetchAllVenues();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_view);

        recyclerView = findViewById(R.id.CustomerEventsRecycler);

        //TODO only display upcoming!
//        ArrayList<String> tests = new ArrayList<>();
//        Event test = new Event("user1", "10am", "11am", 5, 0, 100, tests, "Volleyball tournament", "Volleyball", "08/11/2022");
//        Event test2 = new Event("user1", "10am", "11am", 5, 0, 100, tests, "Volleyball residence cup", "Volleyball", "08/15/2022");
//        Event test3 = new Event("user2", "10am", "11am", 6, 0, 100, tests, "Volleyball residence cupie", "Volleyball", "08/15/2022");
        ArrayList<Event> joinedEvents = customer.fetchJoinedEvents();
//        joinedEvents.add(test);
//        joinedEvents.add(test2);

        ArrayList<Event> scheduledEvents = customer.fetchScheduledEvents();
//        scheduledEvents.add(test);
//        scheduledEvents.add(test3);

        setAdapter(joinedEvents, scheduledEvents, venues);

    }

    private void setAdapter(ArrayList<Event> joinedEvents, ArrayList<Event> scheduledEvents, ArrayList<Venue> venues) {
        HeaderAdapter headerJoined = new HeaderAdapter("Joined Events");
        CustomerEventsAdapter adapterJoined = new CustomerEventsAdapter(joinedEvents, venues);
        HeaderAdapter headerScheduled = new HeaderAdapter("Scheduled Events");
        CustomerEventsAdapter adapterScheduled = new CustomerEventsAdapter(scheduledEvents, venues);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ConcatAdapter cat = new ConcatAdapter(headerJoined, adapterJoined, headerScheduled, adapterScheduled);
        recyclerView.setAdapter(cat);
    }


}