package com.example.gofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    private ImageView profile_pic;
    private ImageView friends_button;
    private BottomNavigationView bottomNavigationView;

     First_Fragment firstFragment = new First_Fragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();
    FourthFragment fourthFragment = new FourthFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        profile_pic.setOnClickListener(this);







        //Temporary image for user profile pic
        Glide.with(this)
                .asBitmap()
                .load("https://thumbs.dreamstime.com/b/default-profile-picture-avatar-photo-placeholder-vector-illustration-default-profile-picture-avatar-photo-placeholder-vector-189495158.jpg")
                .centerCrop()
                .into(profile_pic);

        friends_button = (ImageView) findViewById(R.id.friends_button);
        friends_button.setOnClickListener(this);



        //bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        onNewIntent(getIntent());




    }



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
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, fourthFragment).commit() ;
                set_title.setText("Challenges");
                return true;
        }

        return false;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String extras = intent.getStringExtra("MenuFragment") ;

        if (extras != null && extras.equals("FourthFragment")){
            bottomNavigationView.setSelectedItemId(R.id.challenges);
            Toast.makeText(this, "Sucess!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_pic:
                startActivity(new Intent(this, UserProfile.class));
                break;
            case R.id.friends_button:
                startActivity(new Intent(this, FriendsListPage.class));
        }
    }



}


