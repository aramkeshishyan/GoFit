package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gofit.data.model.requests.Friends;
import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.data.model.responses.tokenResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button login;

//    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private SharedPreferences sp;
    private List<Friends> friendsList = new ArrayList<>();
    private String jsonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

//        mAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);


    }

    //First case: Changes the activity to the Signup page when clicked on "Register"
    //Second case: calls the userLogin function which tries to contact the database
    //Third case: Redirect to ForgotPassword activity page
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Signup.class));
                break;
            case R.id.login:
                userLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

        }

    }


    //Does error checking
    //Then sends the entered user information to the database to check if there is a match and outputs if it's successfully done or not
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        User user = new User(email, "current user", password);
        MainApplication.apiManager.loginUser(user, new Callback<tokenResponse>() {
            @Override
            public void onResponse(Call<tokenResponse> call, Response<tokenResponse> response) {
                tokenResponse responseToken = response.body();


                if (response.isSuccessful() && responseToken.getSuccess() == "true") {

                    Toast.makeText(MainActivity.this,
                            String.format("Login was Successful"),
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                    //storing the token in shared preferences
                    SharedPreferences.Editor spEditor = sp.edit();
                    spEditor.putString("token", responseToken.getAccess_token());
                    spEditor.apply();


                    ///////  LOGIN WAS SUCCESSFUL, WE HAVE THE TOKEN, NOW PASS THE TOKEN AND GET THE USER INFORMATION FROM USER TABLE  /////////////
                    userInfoCall();



                } else {
                    Toast.makeText(MainActivity.this,
                            "Wrong Credentials"
                            , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<tokenResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void userInfoCall(){

        String token = sp.getString("token", "");
        SharedPreferences.Editor spEditor = sp.edit();
        MainApplication.apiManager.getUserInfo(token, new Callback<defaultResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<defaultResponse<UserInfo>> call, Response<defaultResponse<UserInfo>> response) {
                defaultResponse<UserInfo> responseDefault = response.body();

                if (response.isSuccessful() && responseDefault != null) {
                    Toast.makeText(MainActivity.this,
                            "Getting user info was Successful",
                            Toast.LENGTH_SHORT).show();

                    spEditor.putString("fullName", responseDefault.getData().getFullName());
                    spEditor.putString("email", responseDefault.getData().getEmail());
                    spEditor.putString("photoUrl", responseDefault.getData().getPhotoUrl());
                    spEditor.putBoolean("surveyComplete", responseDefault.getData().isSurveyComplete());
                    spEditor.putInt("baseCalories", responseDefault.getData().getBaseCalories());
                    spEditor.putInt("recCalories", responseDefault.getData().getRecCalories());
                    spEditor.putInt("age", responseDefault.getData().getAge());
                    spEditor.putFloat("weight", (float)responseDefault.getData().getWeight());
                    spEditor.putFloat("height", (float)responseDefault.getData().getHeight());
                    spEditor.putString("gender", responseDefault.getData().getGender());
                    spEditor.putString("activityLvl", responseDefault.getData().getActivityLvl());
                    spEditor.putString("bodyType", responseDefault.getData().getBodyType());
                    spEditor.putString("goal", responseDefault.getData().getGoal());
                    spEditor.apply();

                    if(sp.getBoolean("surveyComplete",false)) {
                        startActivity(new Intent(MainActivity.this, HomePage.class));
                    }
                    else{
                        startActivity(new Intent(MainActivity.this, Survey1.class));
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponse<UserInfo>> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());

            }
        });

    }

}