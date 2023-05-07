package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gofit.data.model.requests.EmailDto;
import com.example.gofit.data.model.requests.UserFriendedDeleted;
import com.example.gofit.data.model.requests.UserStats;
import com.example.gofit.data.model.responses.defaultResponse;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBack;
    private Button removeBtn;
    private ImageView imageViewUserImage;
    private TextView textViewUserFullName;

    private TextView textViewEmail;
    private String friendName;
    private String friendEmail;
    private String friendImage;

    //Friend stats
    private TextView stepsNumTxtV;
    private TextView distanceNumTxtV;
    private TextView challengesNumTxtV;
    private TextView totalPointsNumTxtView;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(this);

        removeBtn = findViewById(R.id.removeFriendBtn);
        removeBtn.setOnClickListener(this);

        imageViewUserImage = findViewById(R.id.userProfileImgV);
        textViewUserFullName = findViewById(R.id.userFullName);
        textViewEmail = findViewById(R.id.userEmail);

        //need to use a parseable to parse an Image?
        friendName = getIntent().getStringExtra("NAME");
        friendEmail = getIntent().getStringExtra("EMAIL");
        friendImage = getIntent().getStringExtra("IMAGE");

        textViewUserFullName.setText(friendName);
        textViewEmail.setText(friendEmail);

        // Default image if user has no picture.
        if (friendImage.isEmpty()) {
            friendImage = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }
        Glide.with(this).asBitmap().load(friendImage).centerCrop().into(imageViewUserImage);

        //Friend stats
        stepsNumTxtV = findViewById(R.id.stepsNumTxtV);
        distanceNumTxtV = findViewById(R.id.distanceNumTxtV);
        challengesNumTxtV = findViewById(R.id.challengesNumTxtV);
        totalPointsNumTxtView = findViewById(R.id.totalPointsNumTxtView);

        friendStatsCall();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backBtn:
                super.finish();
                break;
            case R.id.removeFriendBtn:
                deleteFriendCall();
                break;
        }

    }

    private void friendStatsCall(){
        String token = sp.getString("token", "");
        EmailDto email = new EmailDto(friendEmail);

        MainApplication.apiManager.getFriendStats(token, email, new Callback<defaultResponse<UserStats>>() {
            @Override
            public void onResponse(Call<defaultResponse<UserStats>> call, Response<defaultResponse<UserStats>> response) {
                defaultResponse<UserStats> responseFriendStats = response.body();

                if (response.isSuccessful() && responseFriendStats != null) {
                    if (responseFriendStats.getData() != null) {
                        UserStats friendStats = responseFriendStats.getData();
                        String distance = friendStats.getTotalDistanceKm() + " km";

                        stepsNumTxtV.setText(String.valueOf(friendStats.getStepCount()));
                        distanceNumTxtV.setText(distance);
                        challengesNumTxtV.setText(String.valueOf(friendStats.getChallengeCount()));
                        totalPointsNumTxtView.setText(String.valueOf(friendStats.getTotalPoints()));
                    }
                    else {
                        stepsNumTxtV.setText(String.valueOf(0));
                        distanceNumTxtV.setText("0.0 km");
                        challengesNumTxtV.setText(String.valueOf(0));
                        totalPointsNumTxtView.setText(String.valueOf(0));
                    }
                }
                else {
                    Toast.makeText(FriendProfile.this, responseFriendStats.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse<UserStats>> call, Throwable t) {
                Toast.makeText(FriendProfile.this,
                        "Error: ", Toast.LENGTH_LONG).show();
                Log.d("friendStatsCallTag", t.getMessage());
            }
        });



    }

    private void deleteFriendCall() {
        String token = sp.getString("token", "");

        UserFriendedDeleted userDeleted = new UserFriendedDeleted(friendEmail);

        MainApplication.apiManager.deleteFriend(token, userDeleted, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {
                closeProfileAfterDelete();
            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {
                Toast.makeText(FriendProfile.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        });

    }

    private void closeProfileAfterDelete() {
        super.finish();
    }
}