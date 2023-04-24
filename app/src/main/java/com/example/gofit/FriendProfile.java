package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gofit.data.model.requests.UserFriendedDeleted;
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
    private String userName;
    private String userEmail;
    private String userImage;

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
        userName = getIntent().getStringExtra("NAME");
        userEmail = getIntent().getStringExtra("EMAIL");
        userImage = getIntent().getStringExtra("IMAGE");

        textViewUserFullName.setText(userName);
        textViewEmail.setText(userEmail);

        // Default image if user has no picture.
        if (userImage.isEmpty()) {
            userImage = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }
        Glide.with(this).asBitmap().load(userImage).centerCrop().into(imageViewUserImage);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backBtn:
                super.finish();
                //startActivity(new Intent(this, FriendsListPage.class));
                break;
            case R.id.removeFriendBtn:
                deleteFriendCall();

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                startActivity(new Intent(FriendProfile.this, FriendsListPage.class));
                break;
        }

    }

    private void deleteFriendCall() {
        String token = sp.getString("token", "");

        UserFriendedDeleted userDeleted = new UserFriendedDeleted(userEmail);

        MainApplication.apiManager.deleteFriend(token, userDeleted, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {
                defaultResponse<String> responseDelete = response.body();

                    Toast.makeText(FriendProfile.this, String.format("%s Removed",userName), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {
                Toast.makeText(FriendProfile.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        });

    }
}