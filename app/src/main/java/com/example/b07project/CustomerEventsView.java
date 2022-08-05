package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CustomerEventsView extends AppCompatActivity {

    Customer customer;
    private ArrayList<Event> joinedEvents;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_view);

        recyclerView = findViewById(R.id.CustomerEventsRecycler);

        ArrayList<String> tests = new ArrayList<>();
        Event test = new Event("user1", "10am", "11am", 5, 2, 100, tests, "Volleyball tournament", "Volleyball", "08/11/2022");
        Event test2 = new Event("user1", "10am", "11am", 5, 2, 100, tests, "Volleyball residence cup", "Volleyball", "08/15/2022");
        joinedEvents = new ArrayList<>();
        joinedEvents.add(test);
        joinedEvents.add(test2);

        setAdapter();

    }

    private void setAdapter() {
        CustomerEventsAdapter adapter = new CustomerEventsAdapter(joinedEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}