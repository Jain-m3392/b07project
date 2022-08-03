package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

//displays all the venues from the database
//you can click on each venue to see the events happening at that venue
public class CustomerVenuesView extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    ListView listview;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent prev = getIntent();
        customer = prev.getParcelableExtra("Customer");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_venues_view);
        ArrayList<Venue> venues = User.fetchAllVenues();
        listview = (ListView) findViewById(R.id.venueList);

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_venues);
        nav.setOnItemSelectedListener(this);

        //display venues
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, venues);
        VenueAdapter venueAdapter = new VenueAdapter(this, R.layout.venuelist, venues);
        listview.setAdapter(venueAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Venue venue = (Venue) listview.getAdapter().getItem(i);
//                String venueName = (String) listview.getAdapter().getItem(i);
                String venueName = venue.venueName;
                Intent intent = new Intent(CustomerVenuesView.this, customerCreateEvents.class);
                intent.putExtra("Venue", venueName);
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