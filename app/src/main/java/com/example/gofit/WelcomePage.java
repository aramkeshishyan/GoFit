package com.example.gofit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.signInBtn:
        }
    }
}

