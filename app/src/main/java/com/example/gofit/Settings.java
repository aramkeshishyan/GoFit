package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    private ImageButton backBtnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backBtnSettings = findViewById(R.id.backBtnSettings);
        backBtnSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.backBtnSettings:
                super.finish();
                break;
        }
    }
}