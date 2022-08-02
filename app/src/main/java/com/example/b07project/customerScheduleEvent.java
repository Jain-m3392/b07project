package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//for creating an event as a customer
public class customerScheduleEvent extends AppCompatActivity {
    EditText nameInput, capacityInput, startTimeInput, endTimeInput;
    String name, capacity, startTime, endTime;
    Button submit;
    TextView textView;
    DatabaseReference newEventdbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_schedule_event);
        textView = findViewById(R.id.scheduleEventText);
        submit = (Button) findViewById(R.id.submitButton);
        nameInput = (EditText) findViewById(R.id.eventNameUpdate);
        capacityInput = (EditText) findViewById(R.id.eventCapacityUpdate);
        startTimeInput = (EditText) findViewById(R.id.eventStartTimeUpdate);
        endTimeInput = (EditText) findViewById(R.id.eventEndTimeUpdate);
        name = nameInput.getText().toString();
        capacity = capacityInput.getText().toString();
        startTime = startTimeInput.getText().toString();
        endTime = endTimeInput.getText().toString();
        newEventdbRef = FirebaseDatabase.getInstance().getReference().child("venues");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Customer customer = intent.getParcelableExtra("Customer");
                Venue venue = intent.getParcelableExtra("Venue");
                ArrayList<String> customerArray = new ArrayList<String>();
                int eventID = -1;
                for(Event e: User.fetchAllEvents()){
                    if(eventID<e.eventID)
                        eventID = e.eventID;
                }
                Event event = new Event(customer.fullName,startTime, endTime, eventID+1, venue.venueID, Integer.parseInt(capacity), customerArray, name);
                newEventdbRef.push().setValue(event);
                venue.events.add(eventID);
                textView.setText("You successfully created an event!");
            }
        });

    }



}