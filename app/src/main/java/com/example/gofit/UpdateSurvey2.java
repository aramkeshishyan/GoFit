package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gofit.data.model.requests.UpdateSurvey;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.responses.defaultResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSurvey2 extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;

    private ImageButton backBtn;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_survey2);

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        bodyType = getIntent().getStringExtra("bodyType");
        activityLvl = getIntent().getStringExtra("activityLvl");
        goal = getIntent().getStringExtra("goal");

        radioGroupSex = findViewById(R.id.radioGroupSex);

        heightET = findViewById(R.id.heightEditText);
        weightET = findViewById(R.id.weightEditText);
        ageET = findViewById(R.id.ageEditText);

        sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnContinue:
                updateSurveyAnswers();
                super.finish();
                break;
            case R.id.backBtn:
                super.finish();
                break;
        }
    }

    private void updateSurveyAnswers() {
        SharedPreferences.Editor spEditor = sp.edit();

        radioId = radioGroupSex.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        spEditor.putString("bodyType", bodyType);
        spEditor.putString("activityLvl", activityLvl);
        spEditor.putString("goal", goal);

        spEditor.putString("gender", radioButton.getText().toString());
        spEditor.putFloat("height", Float.valueOf(heightET.getText().toString()));
        spEditor.putFloat("weight", Float.valueOf(weightET.getText().toString()));
        spEditor.putInt("age", Integer.valueOf(ageET.getText().toString()));
        spEditor.apply();

        gender = radioButton.getText().toString();
        height = Float.parseFloat(heightET.getText().toString());
        weight = Float.parseFloat(weightET.getText().toString());
        age = Integer.parseInt(ageET.getText().toString());

        makeCalculations();

        updatedUserSurvey = new UpdateSurvey(baseCalories, recCalories, age, weight, height, gender, activityLvl, bodyType, goal);

        pushUserInfo();
    }

    private void pushUserInfo() {
        String token = sp.getString("token", "");
        SharedPreferences.Editor spEditor = sp.edit();

        MainApplication.apiManager.updateUserSurvey(token, updatedUserSurvey, new Callback<defaultResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<defaultResponse<UserInfo>> call, Response<defaultResponse<UserInfo>> response) {
                defaultResponse<UserInfo> userInfoResponse = response.body();

                if (response.isSuccessful() && userInfoResponse != null) {

                    spEditor.putBoolean("surveyComplete", userInfoResponse.getData().isSurveyComplete());
                    spEditor.putInt("baseCalories", userInfoResponse.getData().getBaseCalories());
                    spEditor.putInt("recCalories", userInfoResponse.getData().getRecCalories());
                    spEditor.putInt("age", userInfoResponse.getData().getAge());
                    spEditor.putFloat("weight", (float)userInfoResponse.getData().getWeight());
                    spEditor.putFloat("height", (float)userInfoResponse.getData().getHeight());
                    spEditor.putString("gender", userInfoResponse.getData().getGender());
                    spEditor.putString("activityLvl", userInfoResponse.getData().getActivityLvl());
                    spEditor.putString("bodyType", userInfoResponse.getData().getBodyType());
                    spEditor.putString("goal", userInfoResponse.getData().getGoal());
                    spEditor.apply();

                    Toast.makeText(UpdateSurvey2.this,
                            String.format("User Data Posted Successfully"),
                            Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(UpdateSurvey2.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse<UserInfo>> call, Throwable t) {
                Toast.makeText(UpdateSurvey2.this,
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
    }
}