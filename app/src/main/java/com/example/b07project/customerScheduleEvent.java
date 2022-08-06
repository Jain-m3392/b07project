package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//for creating an event as a customer
public class customerScheduleEvent extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    EditText nameInput, capacityInput, sportsTypeInput, dateInput, startTimeInput, endTimeInput;
    String name, capacity, startTime, endTime, sportsType, dateFinal;
    Button submit;
    TextView textView;
    DatabaseReference newEventdbRef;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_schedule_event);
        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");

        //set up navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_venues);
        nav.setOnItemSelectedListener(this);

        textView = findViewById(R.id.scheduleEventText);
        submit = (Button) findViewById(R.id.submitButton);
        nameInput = (EditText) findViewById(R.id.eventNameUpdate);
        capacityInput = (EditText) findViewById(R.id.eventCapacityUpdate);
        startTimeInput = (EditText) findViewById(R.id.eventStartTimeUpdate);
        endTimeInput = (EditText) findViewById(R.id.eventEndTimeUpdate);
        dateInput = (EditText) findViewById(R.id.eventDateUpdate);
        sportsTypeInput = (EditText) findViewById(R.id.eventSportsUpdate);
        newEventdbRef = FirebaseDatabase.getInstance().getReference().child("events");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Venue venue = intent.getParcelableExtra("Venue");
                ArrayList<String> customerArray = new ArrayList<String>();
                int eventID = -1;
                for (Event e : User.fetchAllEvents()) {
                    if (eventID < e.eventID)
                        eventID = e.eventID;
                }
                name = nameInput.getText().toString();
                capacity = capacityInput.getText().toString();
                sportsType = sportsTypeInput.getText().toString();
                startTime = startTimeInput.getText().toString();
                endTime = endTimeInput.getText().toString();
                dateFinal = dateInput.getText().toString();
                Event event = new Event(customer.fullName, startTime, endTime, eventID + 1, venue.venueID, Integer.parseInt(capacity), customerArray, name, sportsType, dateFinal);
//                newEventdbRef.push().setValue(event);
                event.push();
                customer.addEvent(String.valueOf(eventID + 1));
                customer.push();

                Log.d("Event", venue.events.toString());
                venue.events.add(eventID + 1);
                venue.push();
                //redirecting to the events page after successfully creating an event
                Intent intent = new Intent(customerScheduleEvent.this, CustomerVenuesView.class);
                intent.putExtra("Customer", customer);
                startActivity(intent);
//                textView.setText("You successfully created an event!");
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }
}
