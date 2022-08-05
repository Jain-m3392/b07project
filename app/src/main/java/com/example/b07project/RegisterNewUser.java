package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.b07project.databinding.ActivityRegisterNewUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterNewUser extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityRegisterNewUserBinding binding;

    private EditText editTextUsername, editTextFullName, editTextPassword, editTextEmail;
    private TextView signUp;
    private FirebaseAuth mAuth;
    //private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        signUp = (Button) findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(this);

        editTextUsername = (EditText) findViewById(R.id.username_signup);
        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextFullName = (EditText) findViewById(R.id.full_name);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    /**
     * Called when the user taps the Back button
     */
    public void backToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_button:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Missing Username");
            editTextUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Missing Username");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid Email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Missing Password");
            editTextPassword.requestFocus();
            return;
        }
        if (fullname.isEmpty()) {
            editTextFullName.setError("Missing Full name");
            editTextFullName.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must be 6 or more characters");
            editTextPassword.requestFocus();
            return;
        }

        Customer user = new Customer(username, fullname, email, password);
        checkUsernameAndRegister(user, password);

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//                            Customer user = new Customer(username, fullname, email, password);
//                            user.push();
//                            Toast.makeText(RegisterNewUser.this, "successfully registered user", Toast.LENGTH_LONG).show();
//
////                            FirebaseDatabase.getInstance().getReference("customers")
////                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                                    .child("fullName").setValue(user.fullName);
////
////                            FirebaseDatabase.getInstance().getReference("customers")
////                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                                    .child("password").setValue(user.password);
////
////                            FirebaseDatabase.getInstance().getReference("customers")
////                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                                    .child("username").setValue(user.username);
////
////                            FirebaseDatabase.getInstance().getReference("customers")
////                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                                    .child("email").setValue(user.email).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                        @Override
////                                        public void onComplete(@NonNull Task<Void> task) {
////
////                                            if(task.isSuccessful()){
////                                                Toast.makeText(RegisterNewUser.this, "successfully registered user",Toast.LENGTH_LONG).show();
////                                            }
////                                            else{
////                                                Toast.makeText(RegisterNewUser.this, "failed to register user try again", Toast.LENGTH_LONG).show();
////                                            }
////                                        }
////                                    });
//
//
//                        } else {
//                            Toast.makeText(RegisterNewUser.this, "failed to register user try again", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });


    }

    //Check if username already in use, if not - complete registration
    private void checkUsernameAndRegister(Customer user, String password) {
        RegisterNewUser activity = this;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers/" + user.username);

        //Try find matching user
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
//                Log.d("User", "User username: " + customer.username + " " + customer.fullName + " " + customer.email + " " + customer.password);
                if (customer != null && customer.username != null) {
                    Toast.makeText(RegisterNewUser.this, "Error: username already in use", Toast.LENGTH_LONG).show();
                } else {
                    completeRegistration(user, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }

        });
    }

    private void completeRegistration(Customer user, String password) {

        mAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser fu = mAuth.getCurrentUser();
                            UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName(user.username).build();
                            fu.updateProfile(req);

                            user.push();
                            Toast.makeText(RegisterNewUser.this, "successfully registered user", Toast.LENGTH_LONG).show();

                            Customer customer = (Customer)user;
                            //go to customereventsviewactivity since user signed in upon creation
                            Intent intent = new Intent(RegisterNewUser.this, CustomerEventsView.class);
                            intent.putExtra("Customer", customer);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegisterNewUser.this, "failed to register user try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
