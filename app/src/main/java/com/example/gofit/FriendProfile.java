package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


public class FriendProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBack;
    private ImageView imageViewUserImage;
    private TextView textViewUserFullName;

    private TextView textViewEmail;
    private String userName;
    private String userEmail;
    private String userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(this);

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
        }

    }
}