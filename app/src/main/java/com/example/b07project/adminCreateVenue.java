package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    EditText venueName, venueLocation;
    String name, location;
    Button submit;
    DatabaseReference newVenuedbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_venue);
        textView = findViewById(R.id.venueCreatetext);
        submit = findViewById(R.id.adminCreateVenueButton);
        venueName = (EditText) findViewById(R.id.adminVenueName);
        venueLocation = (EditText) findViewById(R.id.adminVenueLocation);
        name = venueName.getText().toString();
        location = venueLocation.getText().toString();
        newVenuedbRef = User.fetchFirebase().getInstance().getReference().child("venues");

        //Initiate navbar
        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_new_venue);
        nav.setOnItemSelectedListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> events = new ArrayList<>();
                int venueID = -1;
                for(Venue v: User.fetchAllVenues()){
                    if(venueID<v.venueID)
                        venueID = v.venueID;
                }
                Venue newVenue = new Venue(venueID+1, name, location, events);
                newVenuedbRef.push().setValue(newVenue);
                textView.setText("You successfully created a venue!");
//                Toast.makeText(adminCreateVenue.this, "You successfully created a venue!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //TODO
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}