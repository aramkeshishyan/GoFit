package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Survey2 extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContinue:
                startActivity(new Intent(Survey2.this, HomePage.class));
                break;
        }

    }
}