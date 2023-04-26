package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChallengeActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        getIncomingIntent();
        backButton = findViewById(R.id.chal_backButton);
        backButton.setOnClickListener(view -> ChallengeActivity.super.finish());

    }
    private void getIncomingIntent() {

        if(getIntent().hasExtra("chal_title") && getIntent().hasExtra("chal_description"))
        {
            String title = getIntent().getStringExtra("chal_title");
            String description = getIntent().getStringExtra("chal_description");

            setupItems(title, description);
        }


    }

    private void setupItems(String title, String description) {
        TextView mTitle = findViewById(R.id.ch_title);
        mTitle.setText(title);

        TextView mDescription = findViewById(R.id.ch_description);
        mDescription.setText("Description" + description);
    }
}