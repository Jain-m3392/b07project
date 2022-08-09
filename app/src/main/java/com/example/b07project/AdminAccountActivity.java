package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAccountActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    Admin admin;

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
        setContentView(R.layout.activity_admin_account);

        //get data from previous activity
        Intent intent = getIntent();
        this.admin = intent.getParcelableExtra("Admin");

        NavigationBarView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.menuitem_account);
        nav.setOnItemSelectedListener(this);


        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(logoutListener);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AdminNavBar bar = new AdminNavBar();
        return bar.navigate(item, this, admin);

    }
}