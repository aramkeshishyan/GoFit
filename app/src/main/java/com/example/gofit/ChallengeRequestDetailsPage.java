package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ChallengeRequestDetailsPage extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_request_details_page);

        backBtn = findViewById(R.id.challengeReq_backbutton);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.challengeReq_backbutton:
                super.finish();
                break;
        }
    }
}