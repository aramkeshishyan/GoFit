package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private ImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        profile_pic.setOnClickListener(this);








    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_pic:
                startActivity(new Intent(this, UserProfile.class));
                break;
        }
    }
}