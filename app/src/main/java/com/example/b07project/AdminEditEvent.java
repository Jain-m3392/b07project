package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationBarView;

public class AdminEditEvent extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private Admin admin;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_event);

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_home);
        nav.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        this.event = intent.getParcelableExtra("event");
        //identify & set elements
        EditText eventName = findViewById(R.id.eventNameUpdate); eventName.setText(event.name);
        EditText sportsType = findViewById(R.id.eventSportsUpdate); sportsType.setText(event.sportsType);
        EditText eventCapacity = findViewById(R.id.eventCapacityUpdate); eventCapacity.setText(String.valueOf(event.capacity));
        EditText eventDate = findViewById(R.id.eventDateUpdate); eventDate.setText(event.date);
        EditText startTime = findViewById(R.id.eventStartTimeUpdate); startTime.setText(event.startTime);
        EditText endTime = findViewById(R.id.eventEndTimeUpdate); endTime.setText(event.endTime);
        Button edit = findViewById(R.id.editEvent);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update event
                //event.push();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AdminNavBar bar = new AdminNavBar();
        return bar.navigate(item, this, admin);
    }
}