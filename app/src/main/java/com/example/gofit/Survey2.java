package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gofit.data.model.requests.UpdateSurvey;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Survey2 extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;

    private String bodyType;
    private String activityLvl;
    private String goal;

    private String gender;
    private float height;
    private float weight;
    private int age;

    private RadioButton radioButton;
    private RadioGroup radioGroupSex;
    private TextInputEditText heightET;
    private TextInputEditText weightET;
    private TextInputEditText ageET;
    private int radioId;

    private int baseCalories;
    private int recCalories;
    private float basalMetRate;

    private SharedPreferences sp;

    private UpdateSurvey updatedUserSurvey;

    //Bundle extras = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);

        bodyType = getIntent().getStringExtra("bodyType");
        activityLvl = getIntent().getStringExtra("activityLvl");
        goal = getIntent().getStringExtra("goal");

        radioGroupSex = findViewById(R.id.radioGroupSex);

        heightET = findViewById(R.id.heightEditText);
        weightET = findViewById(R.id.weightEditText);
        ageET = findViewById(R.id.ageEditText);

        sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

//        Toast.makeText(Survey2.this, String.format("%s, %s, %s", bodyType,activityLvl,goal),
//                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContinue:
                setSurveyAnswers();
                break;
        }

    }

    public void setSurveyAnswers(){

        SharedPreferences.Editor spEditor = sp.edit();

        radioId = radioGroupSex.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

//        Toast.makeText(Survey2.this, String.format("%s", heightET.getText().toString()),
//               Toast.LENGTH_SHORT).show();

        spEditor.putString("bodyType", bodyType);
        spEditor.putString("activityLvl", activityLvl);
        spEditor.putString("goal", goal);

        spEditor.putString("gender", radioButton.getText().toString());
        spEditor.putString("height", heightET.getText().toString());
        spEditor.putString("weight", weightET.getText().toString());
        spEditor.putString("age", ageET.getText().toString());
        spEditor.apply();

        gender = radioButton.getText().toString();
        height = Float.parseFloat(heightET.getText().toString());
        weight = Float.parseFloat(weightET.getText().toString());
        age = Integer.parseInt(ageET.getText().toString());

        makeCalculations();

        updatedUserSurvey = new UpdateSurvey(baseCalories, recCalories, age, weight, height, gender, activityLvl, bodyType, goal);

        pushUserInfo();

        startActivity(new Intent(Survey2.this, HomePage.class));
    }

    private void pushUserInfo() {
        String token = sp.getString("token", "");

        MainApplication.apiManager.postUserInfo(token, updatedUserSurvey, new Callback<defaultResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<defaultResponse<UserInfo>> call, Response<defaultResponse<UserInfo>> response) {
                defaultResponse<UserInfo> userInfoResponse = response.body();

                if (response.isSuccessful() && userInfoResponse != null) {

                    Toast.makeText(Survey2.this,
                            String.format("User Data Posted Successfully"),
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Survey2.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse<UserInfo>> call, Throwable t) {
                Toast.makeText(Survey2.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());
            }
        });


    }

    private void makeCalculations() {

        //calculate BMR
        if(Objects.equals(gender, "Male"))
        {
            basalMetRate = (float) ((10 * weight) + (6.25 * height) - (5 * age) + 5);
        }
        else
        {
            basalMetRate = (float) ((10 * weight) + (6.25 * height) - (5 * age) - 161);
        }

        //calculate base calories
        if(Objects.equals(activityLvl, "Not Active"))
        {
            baseCalories = (int) (1.2 * basalMetRate);
        }
        else if(Objects.equals(activityLvl, "Lightly Active"))
        {
            baseCalories = (int) (1.375 * basalMetRate);
        }
        else if(Objects.equals(activityLvl, "Moderately Active"))
        {
            baseCalories = (int) (1.55 * basalMetRate);
        }
        else if(Objects.equals(activityLvl, "Very Active"))
        {
            baseCalories = (int) (1.725 * basalMetRate);
        }

        //calculate recommended calories
        if(Objects.equals(goal, "Lose Weight"))
        {
            recCalories = (int) (baseCalories - (baseCalories * 0.15));
        }
        else if(Objects.equals(goal, "Gain Weight"))
        {
            recCalories = baseCalories + 500;
        }

//        Toast.makeText(Survey2.this, String.format("%f", basalMetRate),
//               Toast.LENGTH_SHORT).show();
    }


}