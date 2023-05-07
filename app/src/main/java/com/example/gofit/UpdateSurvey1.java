package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UpdateSurvey1 extends AppCompatActivity implements View.OnClickListener{

    private Button btnContinue;
    private ImageButton btnBack;
    private RadioGroup radioGroupBodyType, radioGroupActivity, radioGroupGoal;
    private RadioButton radioButton;
    private int radioId;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_survey1);

        sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        radioGroupBodyType = findViewById(R.id.radioGroupBodyType);
        radioGroupActivity = findViewById(R.id.radioGroupActivity);
        radioGroupGoal = findViewById(R.id.radioGroupGoal);

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                updateFirstSurveyAnswers();
                super.finish();
                break;
            case R.id.backBtn:
                super.finish();
                break;
        }

    }

        private void updateFirstSurveyAnswers()
        {

            Intent i = new Intent(UpdateSurvey1.this, UpdateSurvey2.class);

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