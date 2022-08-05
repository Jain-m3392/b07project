package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

//for creating an event as a customer
public class customerScheduleEvent extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    EditText nameInput, capacityInput, sportsTypeInput, startTimeInput, endTimeInput;
    String name, capacity, startTime, endTime, sportsType, date;
    Button submit, dateButton;
    TextView textView;
    DatabaseReference newEventdbRef;
    Customer customer;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_schedule_event);
        initDatePicker();
        dateButton.setText(getTodaysDate());

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
        dateButton = findViewById(R.id.eventDateUpdate);
        sportsTypeInput = (EditText) findViewById(R.id.eventSportsUpdate);
        newEventdbRef = FirebaseDatabase.getInstance().getReference().child("events");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Venue venue = intent.getParcelableExtra("Venue");
                ArrayList<String> customerArray = new ArrayList<String>();
                int eventID = -1;
                for(Event e: User.fetchAllEvents()){
                    if(eventID<e.eventID)
                        eventID = e.eventID;
                }
                name = nameInput.getText().toString();
                capacity = capacityInput.getText().toString();
                sportsType = sportsTypeInput.getText().toString();
                startTime = startTimeInput.getText().toString();
                endTime = endTimeInput.getText().toString();
                Event event = new Event(customer.fullName,startTime, endTime, eventID+1, venue.venueID, Integer.parseInt(capacity), customerArray, name, sportsType, date);
//                newEventdbRef.push().setValue(event);
                event.push();
                Log.d("Event", venue.events.toString());
                venue.events.add(eventID + 1);
                venue.push();

                textView.setText("You successfully created an event!");
            }
        });

    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(month, day, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(month, day, year);
                dateButton.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int month, int day, int year) {
        return month + "/" + day + "/" + year;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}