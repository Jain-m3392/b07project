package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    Customer customer;

    //listener for updating customer full name
    private View.OnClickListener updateNameListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //called when update button is pressed

            EditText textBox = findViewById(R.id.editTextTextPersonName);

            customer.fullName = textBox.getText().toString();
            FirebaseDatabase fire = User.fetchFirebase();

            DatabaseReference ref = fire.getReference("customers");
            ref.child(customer.username).setValue(customer);
        }
    };

    //listener for logging out button press
    private View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //get data from previous activity
        Intent intent = getIntent();
        this.customer = intent.getParcelableExtra("Customer");

        //TEMPORARY
//        User.initialize();
        //ArrayList<User> customers = User.fetchAllCustomers();
        //Log.d("debug", customers.toString());
        //this.customer = (Customer)customers.get(0);
//        this.customer = new Customer("test", "test1!", "Test Customer");

        String prevName = customer.fullName;

        //set default value for fullName box
        EditText textBox = findViewById(R.id.editTextTextPersonName);
        textBox.setText(prevName);

        Button updateButton = findViewById(R.id.update_name_button);
        updateButton.setOnClickListener(updateNameListener);

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(logoutListener);
    }

}