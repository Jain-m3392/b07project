package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.b07project.databinding.ActivityMainBinding;
import com.example.b07project.databinding.ActivityRegisterNewUserBinding;

public class RegisterNewUser extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterNewUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
    }

    /** Called when the user taps the Send button */
    public void backToHome(View view) {
        Intent intent = new Intent(this, FirstFragment.class);
        startActivity(intent);
    }


}