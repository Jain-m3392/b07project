package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class AdminEditEvent extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private Admin admin;
    private Event event;
    private Calendar startTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_event);

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        this.event = intent.getParcelableExtra("event");
        this.admin = intent.getParcelableExtra("admin");
        //identify & set elements
        EditText eventName = findViewById(R.id.eventNameUpdate); eventName.setText(event.name);
        EditText sportsType = findViewById(R.id.eventSportsUpdate); sportsType.setText(event.sportsType);
        EditText eventCapacity = findViewById(R.id.eventCapacityUpdate); eventCapacity.setText(String.valueOf(event.capacity));

        //Date & time elements
        EditText eventDate = findViewById(R.id.eventDateUpdate); eventDate.setText(event.date);
        EditText startTime = findViewById(R.id.eventStartTimeUpdate); startTime.setText(event.startTime);
        EditText endTime = findViewById(R.id.eventEndTimeUpdate); endTime.setText(event.endTime);
        eventDate.setInputType(InputType.TYPE_NULL);
        startTime.setInputType(InputType.TYPE_NULL);
        endTime.setInputType(InputType.TYPE_NULL);

        Button edit = findViewById(R.id.editEvent);

        startTime.setOnClickListener(new View.OnClickListener() {
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
                        startTime.setText(time);
                        event.startTime = time;
                    }
                };

                new TimePickerDialog(AdminEditEvent.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
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
                        endTime.setText(time);
                        event.endTime = time;
                    }
                };
                new TimePickerDialog(AdminEditEvent.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });


        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        month += 1; //In Java months are indexed starting at 0
                        String calendarDate = month + "/" + day + "/" + year;
                        eventDate.setText(calendarDate);
                        event.date = calendarDate;
                    }
                };

                DatePickerDialog newDPD = new DatePickerDialog(AdminEditEvent.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                newDPD.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                newDPD.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update event fields
                event.name = eventName.getText().toString();
                event.sportsType = sportsType.getText().toString();
                event.capacity = Integer.valueOf(eventCapacity.getText().toString());

                //update Firebase
                event.push();
                Toast.makeText(getApplicationContext(), "Event updated", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AdminNavBar bar = new AdminNavBar();
        return bar.navigate(item, this, admin);
    }
}