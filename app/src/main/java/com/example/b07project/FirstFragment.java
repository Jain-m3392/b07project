package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07project.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirstFragment extends Fragment implements View.OnClickListener{

    private FragmentFirstBinding binding;
    private TextView signup;
    private EditText editTextEmail, editTextPassword;
    private Button login;
    private FirebaseAuth mAuth;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        mAuth=FirebaseAuth.getInstance();
        signup = (TextView) view.findViewById(R.id.sign_up);
        signup.setOnClickListener(this);
        login = (Button) view.findViewById(R.id.login_button);
        login.setOnClickListener(this);
        editTextEmail = (EditText) view.findViewById((R.id.email_edit_text));
        editTextPassword = (EditText) view.findViewById(R.id.password_edit_text);


        /*binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterNewUser.class);
                startActivity(intent);
                //NavHostFragment.findNavController(FirstFragment.this)
                //        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_button:
                loginUser();
                break;
            case R.id.sign_up:
                Intent intent = new Intent(getActivity(), RegisterNewUser.class);
                startActivity(intent);
                break;
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Missing Email");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Invalid Email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Missing Password");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Invalid password");
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //&& FirebaseAuth.getInstance().getCurrentUser().getClass() instanceof Customer
                if(task.isSuccessful()){
                    //go to customereventsviewactivity

                    Intent intent = new Intent(getActivity(), CustomerVenuesView.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Login failed, username or password incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}