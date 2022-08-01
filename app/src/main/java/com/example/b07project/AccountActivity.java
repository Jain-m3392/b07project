package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    Customer customer;

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

    }

    //called when update button is pressed
    public void updateName(View view){
        EditText textBox = findViewById(R.id.editTextTextPersonName);

        this.customer.fullName = textBox.getText().toString();
        FirebaseDatabase fire = User.fetchFirebase();

        DatabaseReference ref = fire.getReference("customers");
        ref.child(this.customer.username).setValue(customer);
    }

    //called when logout button is pressed
    public void logOut(View view){

        //NO NEED TO CLOSE CONNECTION SINCE WILL RECONNECT TO SAME DATABASE WHEN LOG IN ACTIVITY IS LOADED
        //close firebase connection
//        FirebaseDatabase fire = User.fetchFirebase();
//        fire.goOffline();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}