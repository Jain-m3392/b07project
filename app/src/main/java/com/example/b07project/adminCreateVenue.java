package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//for creating a venue as an admin
public class adminCreateVenue extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    TextView textView;
    EditText venueName, venueLocation, venueSports;
    String name, location, sports;
    Button submit, add;
    DatabaseReference newVenuedbRef;
    Admin admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        admin = intent.getParcelableExtra("Admin");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_venue);
        textView = findViewById(R.id.venueCreatetext);
        submit = findViewById(R.id.adminCreateVenueButton);
        add = findViewById(R.id.adminAddButton);
        venueName = (EditText) findViewById(R.id.adminVenueName);
        venueLocation = (EditText) findViewById(R.id.adminVenueLocation);
        venueSports = (EditText) findViewById(R.id.adminVenueSports);
        newVenuedbRef = User.fetchFirebase().getInstance().getReference().child("venues");

        //Initiate navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_new_venue);
        nav.setOnItemSelectedListener(this);

        //Initiate sports addition listener
        ArrayList<String> sportsTypes = new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sports = venueSports.getText().toString();
                sportsTypes.add(sports);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> events = new ArrayList<>();
                int venueID = -1;
                for(Venue v: User.fetchAllVenues()){
                    if(venueID<v.venueID)
                        venueID = v.venueID;
                }

                name = venueName.getText().toString();
                location = venueLocation.getText().toString();

                Venue newVenue = new Venue(venueID+1, name, location, events, sportsTypes);

                newVenue.push();
                textView.setText("You successfully created a venue!");
//                Toast.makeText(adminCreateVenue.this, "You successfully created a venue!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AdminNavBar bar = new AdminNavBar();
        return bar.navigate(item, this, admin);
    }
}