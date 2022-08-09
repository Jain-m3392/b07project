package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    Customer customer;

    //listener for updating customer full name
    private View.OnClickListener updateNameListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //called when update button is pressed

            EditText textBox = findViewById(R.id.editTextTextPersonName);

            customer.fullName = textBox.getText().toString();
            FirebaseDatabase fire = User.fetchFirebase();

            customer.push();
        }
    };

    //listener for logging out button press
    private View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //get data from previous activity
        Intent intent = getIntent();
        this.customer = intent.getParcelableExtra("Customer");

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_account);
        nav.setOnItemSelectedListener(this);

        String prevName = customer.fullName;

        //set default value for fullName box
        EditText textBox = findViewById(R.id.editTextTextPersonName);
        textBox.setText(prevName);

        Button updateButton = findViewById(R.id.update_name_button);
        updateButton.setOnClickListener(updateNameListener);

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(logoutListener);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavBar bar = new NavBar();
        return bar.navigate(item, this, customer);
       // return true;
    }
}