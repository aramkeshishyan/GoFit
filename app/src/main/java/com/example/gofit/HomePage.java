package com.example.gofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private ImageView profile_pic;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        profile_pic.setOnClickListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        ///
                        return true;
                    case R.id.exercise:
                        startActivity(new Intent(HomePage.this, ExercisePage.class));
                        return true;
                    case R.id.nutrition:
                        startActivity(new Intent(HomePage.this, NutritionPage.class));
                        return true;
                    case R.id.challenges:
                        startActivity(new Intent(HomePage.this, challenge.class));
                        return true;
                    default: return true;
                }

            }
        });




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