package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.requests.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity implements View.OnClickListener {


    private TextView banner, registerUser, loginText;
    private EditText editTextFullName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        loginText = (TextView) findViewById(R.id.loginText);
        loginText.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullname);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    //First case goes to the main page when clicking on the banner "GoFit"
    //When clicking "REGISTER USER" calls the registerUser function
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
            case R.id.loginText:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }

    }


    //The function takes input and pushes it to the database
    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();

        //The function takes Fullname, Email, Password inputs and does error checking, empty filed checking

        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;

        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        //If all the fields are filled in correct, pushes the data to the database and checks if it was received successfully or not.
        //Displays toast messages if push was success or failed
        //progressBar.setVisibility(View.VISIBLE);


        User user = new User(email, fullName, password);
        MainApplication.apiManager.createUser(user, new Callback<defaultResponse<Integer>>() {
            @Override
            public void onResponse(Call<defaultResponse<Integer>> call, Response<defaultResponse<Integer>> response) {
                defaultResponse<Integer> responseUser = response.body();
                if (response.isSuccessful() && responseUser != null) {
                    Toast.makeText(Signup.this,
                                    String.format("User Registration was Successful",
                                            responseUser.isSuccess(),
                                            responseUser.getData(),
                                            responseUser.getMessage()),
                                    Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Signup.this,MainActivity.class));
                } else {
                    Toast.makeText(Signup.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse<Integer>> call, Throwable t) {
                Toast.makeText(Signup.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });









        /*            FIREBASE REGISTRATION
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(Signup.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(Signup.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(Signup.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });*/

    }
}