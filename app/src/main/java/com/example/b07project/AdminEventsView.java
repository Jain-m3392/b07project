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

public class AdminEventsView extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    Admin admin;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        admin = intent.getParcelableExtra("Admin");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events_view);

        recyclerView = findViewById(R.id.AdminEventsRecycler);

        ArrayList<Venue> venues = admin.fetchCreatedVenues();
        ArrayList<Event> events = new ArrayList<>();

        //Initiate navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_home);
        nav.setOnItemSelectedListener(this);

        //fetch all events at current wanted venues
        for (Venue v: venues){
            events.addAll(v.fetchEvents());
        }

        setAdapter(events, venues);

    }

    private void setAdapter(ArrayList<Event> events, ArrayList<Venue> venues){
        //initialize set adapter
        HeaderAdapter header = new HeaderAdapter("Events scheduled at your venues");
        AdminEventsAdapter adapter = new AdminEventsAdapter(events, venues);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ConcatAdapter cat = new ConcatAdapter(header, adapter);
        recyclerView.setAdapter(cat);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AdminNavBar bar = new AdminNavBar();
        return bar.navigate(item, this, admin);
    }
}