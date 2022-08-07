package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;

import com.example.b07project.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        User.initialize();

        //CREATE ADMIN IN AUTHENTICATOR API
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.createUserWithEmailAndPassword("admin@gmail.com", "admin123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//             @Override
//             public void onComplete(@NonNull Task<AuthResult> task) {
//                 FirebaseUser fu = auth.getCurrentUser();
//                 UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName("admin").build();
//                 fu.updateProfile(req);
//             }
//         });


//        ArrayList<String[]> users = new ArrayList<String[]>();
//        users.add(new String[]{"user@gmail.com", "user123", "testUser"});
//        users.add(new String[]{"peach@gmail.com", "coconut", "apple"});
//        users.add(new String[]{"test@gmail.com", "test123", "test"});
//        users.add(new String[]{"s@gmail.com", "signup", "s"});
//        for (String[] U: users){
//            FirebaseAuth.getInstance().signInWithEmailAndPassword(U[0], U[1]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        Log.d("User", "Fixing: " + U[0].toString());
//                        FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser();
//                        Log.d("User", "email: " + fu.getEmail());
//                        UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName(U[2]).build();
//                        fu.updateProfile(req);
//                        FirebaseAuth.getInstance().signOut();
//                    }
//                }
//            });
//        }


                binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void completeLogin(User user) {
//        Log.d("User", "Customer joinedEvents: " + ((Customer)user).joinedEvents);
        if (user instanceof Customer){
            Customer customer = (Customer)user;
            //go to customereventsviewactivity
            Intent intent = new Intent(this, CustomerEventsView.class);
            intent.putExtra("Customer", customer);
            startActivity(intent);
        }

        else if (user instanceof Admin){
            Admin admin = (Admin)user;
            Intent intent = new Intent(this, AdminEventsView.class);
            intent.putExtra("Admin", admin);
            Log.d("login", admin.venues.toString());
            startActivity(intent);

        }
    }



    //get the given customer or admin
    public void findUserandLogIn(String fbid){
        Log.d("User", "Userid: '"+ fbid +"'");
        MainActivity activity = this;
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers").child(fbid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers/" + fbid);

        final User[] user = {null};

        //Try find matching customer
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("User", "found");
//                user[0] = snapshot.getValue(Customer.class);
                Customer customer = snapshot.getValue(Customer.class);
//                Log.d("User", "User username: " + customer.username + " " + customer.fullName + " " + customer.email + " " + customer.password);
                if (customer != null && customer.email != null){
                    if (customer.joinedEvents == null)
                        customer.joinedEvents = new ArrayList<String>();
                    if (customer.scheduledEvents == null)
                        customer.scheduledEvents = new ArrayList<String>();
                    activity.completeLogin(customer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("admins").child(fbid);
        //Try find matching admin
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin admin = snapshot.getValue(Admin.class);
                if (admin != null){
                    if (admin != null && admin.email != null){
                        if (admin.venues == null){
                            admin.venues = new ArrayList<Integer>();
                        }

                        activity.completeLogin(admin);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", error.toString());
            }
        });

    }

}