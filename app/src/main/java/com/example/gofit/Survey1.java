package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Survey1 extends AppCompatActivity implements View.OnClickListener{

    private Button btnContinue;
    private RadioGroup radioGroupBodyType, radioGroupActivity, radioGroupGoal;
    private RadioButton radioButton;
    private int radioId;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey1);

        radioGroupBodyType = findViewById(R.id.radioGroupBodyType);
        radioGroupActivity = findViewById(R.id.radioGroupActivity);
        radioGroupGoal = findViewById(R.id.radioGroupGoal);

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);

        sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnContinue) {
            firstSurveyAnswers();
            super.finish();
        }
    }


    private void firstSurveyAnswers()
    {

        Intent i = new Intent(Survey1.this, Survey2.class);

        radioId = radioGroupBodyType.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        i.putExtra("bodyType", radioButton.getText().toString());


        radioId = radioGroupActivity.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


        i.putExtra("activityLvl", radioButton.getText().toString());


        radioId = radioGroupGoal.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


        i.putExtra("goal", radioButton.getText().toString());



        startActivity(i);
    }
}