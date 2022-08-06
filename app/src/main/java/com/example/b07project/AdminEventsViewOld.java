package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminEventsViewOld extends AppCompatActivity {

    Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        admin = intent.getParcelableExtra("Admin");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events_view_old);

        ArrayList<Venue> venues = admin.fetchCreatedVenues();

        LinearLayout llVenuesView = findViewById(R.id.llVenuesView);
        TextView title = new TextView(this);
        title.setText(R.string.admin_venues);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        title.setLayoutParams(params);
        llVenuesView.addView(title);

        TextView blank = new TextView(this);
        blank.setText("");
        blank.setLayoutParams(params);
        blank.setTextSize(5);
        llVenuesView.addView(blank);

        if (venues.size() == 0){
            TextView no_venues = new TextView(this);
            no_venues.setText(R.string.no_venues);
            no_venues.setLayoutParams(params);
            no_venues.setTextSize(15);
            llVenuesView.addView(no_venues);
        }
        //display all for now TODO filter tomorrow
        else{
            for(Venue v: venues) {
                TextView name = new TextView(this);
                name.setText(v.venueName);
                name.setLayoutParams(params);
                name.setTextSize(15);
                llVenuesView.addView(name);
                TextView location = new TextView(this);
                location.setText(v.venueLocation);
                location.setLayoutParams(params);
                location.setTextSize(15);
                llVenuesView.addView(location);
                TextView blanklist = new TextView(this);
                blanklist.setText("");
                blanklist.setLayoutParams(params);
                blanklist.setTextSize(5);
                llVenuesView.addView(blanklist);
            }
        }

    }
}