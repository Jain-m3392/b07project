package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//displays all the venues from the database
//you can click on each venue to see the events happening at that venue
public class CustomerVenuesView extends AppCompatActivity {

    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_venues_view);
        ArrayList<Venue> venues = User.fetchAllVenues();
        listview = (ListView) findViewById(R.id.venueList);
        //display venues
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, venues);
        VenueAdapter venueAdapter = new VenueAdapter(this, R.layout.venuelist, venues);
        listview.setAdapter(venueAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String venueName = (String) listview.getAdapter().getItem(i);
                Intent intent = new Intent(CustomerVenuesView.this, customerCreateEvents.class);
                intent.putExtra("Venue", venueName);
                startActivity(intent);
            }
        });

    }
}