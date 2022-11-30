package com.example.gofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    private ImageView profile_pic;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        profile_pic.setOnClickListener(this);

        //bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    First_Fragment firstFragment = new First_Fragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();
    FourthFragment fourthFragment = new FourthFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        TextView set_title = findViewById(R.id.title_text);
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, firstFragment).commit();
                set_title.setText("Home");
                return true;
            case R.id.exercise:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, secondFragment).commit();
                set_title.setText("Exercise");
                return true;
            case R.id.nutrition:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, thirdFragment).commit();
                set_title.setText("Nutrition");
                return true;
            case R.id.challenges:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, fourthFragment).commit();
                set_title.setText("Challenges");
                return true;
        }
        return false;
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