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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//for creating an event as a customer
public class customerScheduleEvent extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    EditText nameInput, capacityInput, dateInput, startTimeInput, endTimeInput;
    String name, capacity, startTime, endTime, sportsType, dateFinal;
    Button submit;
//    TextView textView;
//    DatabaseReference newEventdbRef;
    Customer customer;
    Calendar startTC;
    Spinner dropdown;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_schedule_event);
        Intent intent = getIntent();
        customer = intent.getParcelableExtra("Customer");
        Venue venue = intent.getParcelableExtra("Venue");
//        String[] accessibleSports = new String [venue.accessibleSports.size()];
//        for(int i=0; i<venue.accessibleSports.size(); i++){
//            accessibleSports[i] = venue.accessibleSports.get(i);
//        }

        //set up navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_venues);
        nav.setOnItemSelectedListener(this);

//        textView = findViewById(R.id.scheduleEventText);
        submit = (Button) findViewById(R.id.submitButton);
        nameInput = (EditText) findViewById(R.id.eventNameUpdate);
        capacityInput = (EditText) findViewById(R.id.eventCapacityUpdate);
        startTimeInput = (EditText) findViewById(R.id.eventStartTimeUpdate);
        endTimeInput = (EditText) findViewById(R.id.eventEndTimeUpdate);
        dateInput = (EditText) findViewById(R.id.eventDateUpdate);
        dropdown = (Spinner) findViewById(R.id.dropdownSports);
        dateInput.setInputType(InputType.TYPE_NULL);
        startTimeInput.setInputType(InputType.TYPE_NULL);
        endTimeInput.setInputType(InputType.TYPE_NULL);
//        adapter = new ArrayAdapter<String>(customerScheduleEvent.this, R.layout.dropdown_item, accessibleSports);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(customerScheduleEvent.this, android.R.layout.simple_spinner_item, accessibleSports);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(customerScheduleEvent.this, android.R.layout.simple_spinner_item, venue.accessibleSports);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        Log.d("Schedule", venue.accessibleSports.toString());
//        newEventdbRef = FirebaseDatabase.getInstance().getReference().child("events");

        startTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        String time = selectedHour + ":" + selectedMinute;
                        startTC = calendar;
                        startTimeInput.setText(time);
                        startTime = time;
                    }
                };

                new TimePickerDialog(customerScheduleEvent.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });

        endTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showTimeDialog(endTimeInput);
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        String time =null;
                        if(calendar.getTimeInMillis() >= startTC.getTimeInMillis()){
                            time = selectedHour + ":" + selectedMinute;
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Invalid time, please choose again", Toast.LENGTH_LONG).show();
                        }
                        endTimeInput.setText(time);
                        endTime = time;
                    }
                };
                new TimePickerDialog(customerScheduleEvent.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });


        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String calendarDate = month + "/" + day + "/" + year;
                        dateInput.setText(calendarDate);
                        dateFinal = calendarDate;
                    }
                };

                DatePickerDialog newDPD = new DatePickerDialog(customerScheduleEvent.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                newDPD.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                newDPD.show();
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sportsType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(customerScheduleEvent.this, "Please select sports type", Toast.LENGTH_LONG).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> customerArray = new ArrayList<String>();
                int eventID = -1;
                for (Event e : User.fetchAllEvents()) {
                    if (eventID < e.eventID)
                        eventID = e.eventID;
                }
                name = nameInput.getText().toString();
                capacity = capacityInput.getText().toString();
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