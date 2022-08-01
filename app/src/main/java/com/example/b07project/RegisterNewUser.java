package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b07project.databinding.ActivityMainBinding;
import com.example.b07project.databinding.ActivityRegisterNewUserBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterNewUser extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterNewUserBinding binding;

    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword;
    private TextView signUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        mAuth = FirebaseAuth.getInstance();

        signUp = (Button) findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.username_signup);
        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
        editTextConfirmPassword = (EditText) findViewById(R.id.password_confirmation_signup);
    }

    /** Called when the user taps the Back button */
    public void backToHome(View view) {
        Intent intent = new Intent(this, FirstFragment.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_up_button:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String username = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmpass = editTextConfirmPassword.getText().toString().trim();

        if(username.isEmpty()){
            editTextName.setError("Missing Username");
            editTextName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Missing Email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Missing Password");
            editTextPassword.requestFocus();
            return;
        }
        if(confirmpass.isEmpty()){
            editTextConfirmPassword.setError("Retype Password to Confirm");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Password must be 6 or more characters");
            editTextPassword.requestFocus();
            return;
        }


    }
}