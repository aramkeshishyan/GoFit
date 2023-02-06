package com.example.gofit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.gofit.data.model.UserRegister;
import com.example.gofit.data.remote.ApiInterface;
import com.example.gofit.data.remote.RetrofitService;
import com.example.gofit.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView banner, registerUser, loginText;
    private EditText editTextFullName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

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











        ApiInterface apiInterface = RetrofitService.getRetrofitInstance().create(ApiInterface.class);
        Call<UserRegister> call = apiInterface.getUserInformation("testingName", "testingEmailgotfit@yahoo.com", "testpassword");
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                Log.e(TAG, "OnResponse: " + response.code());
                //Log.e(TAG, "OnResponse: success : " + response.body().isSuccess());
                //Log.e(TAG, "OnResponse: data : " + response.body().getData());
                //Log.e(TAG, "OnResponse: message : " + response.body().getMessage());

            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

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