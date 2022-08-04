package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class CustomerEventsViewActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//      get data from previous activity
        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_view);

        //testing
        ArrayList<String> tests = new ArrayList<>();
        Event test = new Event("user1", "10am", "11am", 5, 2, 100, tests, "Volleyball");
        Event test2 = new Event("user1", "10am", "11am", 5, 2, 100, tests, "Volleyball");

//      TODO - set login class to send data to this activity


        //for testing purposes
        ArrayList<Event> joinedEvents = new ArrayList<>();
        joinedEvents.add(test);
        joinedEvents.add(test2);

//        ArrayList<Event> joinedEvents = customer.fetchJoinedEvents();
        ArrayList<Event> scheduledEvents = customer.fetchScheduledEvents();

        //testing
        Log.i("general", "array size: " + joinedEvents.size());

        //set up navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_home);
        nav.setOnItemSelectedListener(this);

        LinearLayout llEventsView = findViewById(R.id.llEventsView);

        //displaying scheduled events
        TextView scheduled = new TextView(this);
        scheduled.setText(R.string.scheduled_events);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        scheduled.setLayoutParams(params);
        scheduled.setTypeface(null, Typeface.BOLD);
        scheduled.setTextSize(20);
        llEventsView.addView(scheduled);
        //testing
        Log.i("gen", "size is " + scheduledEvents.size());

        if (scheduledEvents.size() == 0){
            TextView no_scheduled = new TextView(this);
            no_scheduled.setText(R.string.no_events_to_show);
            no_scheduled.setLayoutParams(params);
            no_scheduled.setTextSize(15);
            llEventsView.addView(no_scheduled);
        }
        else{
            for (Event e: scheduledEvents){
//            Log.i("event info", e.name);
                TextView name = new TextView(this);
                name.setText(e.name);
                name.setLayoutParams(params);
                name.setTextSize(15);
                llEventsView.addView(name);
                TextView time = new TextView(this);
                time.setText(getString(R.string.display_time, e.startTime, e.endTime));
                time.setLayoutParams(params);
                time.setTextSize(15);
                llEventsView.addView(time);
                TextView creator = new TextView(this);
                creator.setText(getString(R.string.event_creator, e.creator));
                creator.setLayoutParams(params);
                creator.setTextSize(15);
                llEventsView.addView(creator);
                TextView blanklistsch = new TextView(this);
                blanklistsch.setText("");
                blanklistsch.setLayoutParams(params);
                blanklistsch.setTextSize(5);
                llEventsView.addView(blanklistsch);
            }
        }

        TextView blank = new TextView(this);
        blank.setText("");
        blank.setLayoutParams(params);
        blank.setTextSize(5);
        llEventsView.addView(blank);

        //displaying joined events
        TextView joined = new TextView(this);
        joined.setText(R.string.joined_events);
        joined.setLayoutParams(params);
        joined.setTextSize(20);
        joined.setTypeface(null, Typeface.BOLD);
        llEventsView.addView(joined);
        if (joinedEvents.size() == 0){
            TextView no_joined = new TextView(this);
            no_joined.setText(R.string.no_events_to_show);
            no_joined.setLayoutParams(params);
            no_joined.setTextSize(15);
            llEventsView.addView(no_joined);
        }
        else{
            for (Event e: joinedEvents){
                TextView name = new TextView(this);
                name.setText(e.name);
                name.setLayoutParams(params);
                name.setTextSize(15);
                llEventsView.addView(name);
                TextView time = new TextView(this);
                time.setText(getString(R.string.display_time, e.startTime, e.endTime));
                time.setLayoutParams(params);
                time.setTextSize(15);
                llEventsView.addView(time);
                TextView creator = new TextView(this);
                creator.setText(getString(R.string.event_creator, e.creator));
                creator.setLayoutParams(params);
                creator.setTextSize(15);
                llEventsView.addView(creator);
                TextView blanklistjoin = new TextView(this);
                blanklistjoin.setText("");
                blanklistjoin.setLayoutParams(params);
                blanklistjoin.setTextSize(5);
                llEventsView.addView(blanklistjoin);
            }
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }
}