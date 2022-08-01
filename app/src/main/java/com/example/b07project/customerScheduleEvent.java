package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class customerScheduleEvent extends AppCompatActivity {
    EditText nameInput, capacityInput, startTimeInput, endTimeInput;
    String name, capacity, startTime, endTime;
    Button submit;
    TextView textView;
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Customer customer = intent.getParcelableExtra("Customer");
                Venue venue = intent.getParcelableExtra("Venue");
                ArrayList<String> customerArray = new ArrayList<String>();
                Event event = new Event(customer.fullName,startTime, endTime, "EventID", venue.venueID, capacity, customerArray, name);
                venue.addEvent(event);
                textView.setText("You successfully created an event!");
            }
        });

    }



}