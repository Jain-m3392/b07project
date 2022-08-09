package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class CustomerEventsView extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    Customer customer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");
        ArrayList<Venue> venues = customer.fetchAllVenues();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_view);

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_home);
        nav.setOnItemSelectedListener(this);

        recyclerView = findViewById(R.id.CustomerEventsRecycler);
        ArrayList<Event> joinedEvents = customer.fetchJoinedEvents();


        ArrayList<Event> scheduledEvents = customer.fetchScheduledEvents();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }
}