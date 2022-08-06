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
//        dateButton.setText(getTodaysDate());
//        initDatePicker();
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
//        startTimeButton = (Button) findViewById(R.id.eventStartTimeUpdate);
//        endTimeButton = (Button) findViewById(R.id.eventEndTimeUpdate);
//        dateButton = (Button) findViewById(R.id.eventDateUpdate);
//        dateButton.setText(getTodaysDate());

        startTimeInput = (EditText) findViewById(R.id.eventStartTimeUpdate);
        endTimeInput = (EditText) findViewById(R.id.eventEndTimeUpdate);
        dateInput = (EditText) findViewById(R.id.eventDateUpdate);
        sportsTypeInput = (EditText) findViewById(R.id.eventSportsUpdate);
        dateInput.setInputType(InputType.TYPE_NULL);
        startTimeInput.setInputType(InputType.TYPE_NULL);
        endTimeInput.setInputType(InputType.TYPE_NULL);
        newEventdbRef = FirebaseDatabase.getInstance().getReference().child("events");

        startTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        startTimeInput.setText(simpleDateFormat.format(calendar));
                    }
                };

                new TimePickerDialog(customerScheduleEvent.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
//                showTimeDialog(startTimeInput);
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        endTimeInput.setText(simpleDateFormat.format(calendar));
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-mm-dd");
                        dateInput.setText(simpleDateFormat.format(calendar));
                    }
                };

                new DatePickerDialog(customerScheduleEvent.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                dateFinal = dateInput.getText().toString();
                Event event = new Event(customer.fullName,startTime, endTime, eventID+1, venue.venueID, Integer.parseInt(capacity), customerArray, name, sportsType, dateFinal);
//                newEventdbRef.push().setValue(event);
                event.push();
                customer.addEvent(String.valueOf(eventID + 1));
                customer.push();

                Log.d("Event", venue.events.toString());
                venue.events.add(eventID + 1);
                venue.push();
                //redirecting to the events page after successfully creating an event
                Intent intent = new Intent(customerScheduleEvent.this, customerCreateEvents.class);
                startActivity(intent);
//                textView.setText("You successfully created an event!");
            }
        });

    }


//    private void showTimeDialog(EditText startTimeInput) {
//        Calendar calendar = Calendar.getInstance();
//        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
//                calendar.set(Calendar.MINUTE, selectedMinute);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//                startTimeInput.setText(simpleDateFormat.format(calendar));
//            }
//        };
//
//        new TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
//
//    }

//    private void showDateDialog(EditText dateInput) {
//        Calendar calendar = Calendar.getInstance();
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, month);
//                calendar.set(Calendar.DAY_OF_MONTH, day);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-mm-dd");
//                dateInput.setText(simpleDateFormat.format(calendar));
//            }
//        };
//
//        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }

//    public void openDatePicker(View view) {
//        datePickerDialog.show();
//    }

//    private String getTodaysDate() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        month = month+1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        return makeDateString(month, day, year);
//    }

//    private void initDatePicker() {
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month + 1;
//                String date = makeDateString(month, day, year);
//                dateButton.setText(date);
//                dateFinal = date;
////                dateFinal = dateButton.getText().toString();
//            }
//        };
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        int style = AlertDialog.THEME_HOLO_LIGHT;
//        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//    }

//    private String makeDateString(int month, int day, int year) {
//        return month + "/" + day + "/" + year;
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
    }



//    public void popTimePicker(View view) {
//        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                hour = selectedHour;
//                minute = selectedMinute;
//                startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
////                startTime = startTimeButton.getText().toString();
//                startTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
//            }
//        };
//        int style = AlertDialog.THEME_HOLO_LIGHT;
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style, onTimeSetListener, hour, minute, true);
//        timePickerDialog.show();
//    }

//    public void popTimePicker2(View view) {
//        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                hour = selectedHour;
//                minute = selectedMinute;
//                endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
////                endTime = endTimeButton.getText().toString();
//                endTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
//            }
//        };
//        int style = AlertDialog.THEME_HOLO_LIGHT;
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style, onTimeSetListener, hour, minute, true);
//        timePickerDialog.show();
//    }
}