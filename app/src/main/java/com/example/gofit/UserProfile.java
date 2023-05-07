package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.gofit.data.model.requests.UserStats;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.FriendRecyclerViewInterface;
import com.example.gofit.recyclerViews.FriendsRecViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity implements View.OnClickListener, FriendRecyclerViewInterface {

    private ImageButton logout;
    private ImageButton backBtn;
    private ImageButton settingsBtn;

    private ImageView userProfileImgV;
    private TextView textViewUserFullName;
    private TextView textViewEmail;

    private RecyclerView friendsRecView;
    private Button friendsViewAllBtn;

    //Stats
    private TextView stepsNumTxtV;
    private TextView distanceNumTxtV;
    private TextView challengesNumTxtV;
    private TextView totalPointsNumTxtView;

    private SharedPreferences sp;

    FriendsRecViewAdapter adapter = new FriendsRecViewAdapter(this, this);
    Context context = this;
    private ArrayList<Friend> friendsList = new ArrayList<>();

    private String userName;
    private String userEmail;
    private String userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(this);

        //opens friends list page activity
        friendsViewAllBtn = findViewById(R.id.friendsViewAllBtn);
        friendsViewAllBtn.setOnClickListener(this);

        //logout button functionality
        logout = findViewById(R.id.logOutBtn);
        logout.setOnClickListener(this);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);


        textViewUserFullName = findViewById(R.id.userFullName);
        textViewEmail = findViewById(R.id.userEmail);
        userProfileImgV = findViewById(R.id.userProfileImgV);

        //Stats views
        stepsNumTxtV = findViewById(R.id.stepsNumTxtV);
        distanceNumTxtV = findViewById(R.id.distanceNumTxtV);
        challengesNumTxtV = findViewById(R.id.challengesNumTxtV);
        totalPointsNumTxtView = findViewById(R.id.totalPointsNumTxtView);

        userFriendsCall();
        userInfo();
        userStatsCall();


    }

    private void userInfo() {
        userName = sp.getString("fullName","");
        userEmail = sp.getString("email","");
        userImage = sp.getString("photoUrl","");

        textViewUserFullName.setText(userName);
        textViewEmail.setText(userEmail);

        //default image if user has no photo
        if (userImage.isEmpty()) {
            userImage = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }
        Glide.with(this).asBitmap().load(userImage).centerCrop().into(userProfileImgV);
    }

    private void userStatsCall(){
        String token = sp.getString("token", "");
        MainApplication.apiManager.getUserStats(token, new Callback<defaultResponse<UserStats>>() {
            @Override
            public void onResponse(Call<defaultResponse<UserStats>> call, Response<defaultResponse<UserStats>> response) {
                defaultResponse<UserStats> responseUserStats = response.body();

                if (response.isSuccessful() && responseUserStats != null) {
                    UserStats stats = responseUserStats.getData();
                    String distance = stats.getTotalDistanceKm() + " km";

                    stepsNumTxtV.setText(String.valueOf(stats.getStepCount()));
                    distanceNumTxtV.setText(distance);
                    challengesNumTxtV.setText(String.valueOf(stats.getChallengeCount()));
                    totalPointsNumTxtView.setText(String.valueOf(stats.getTotalPoints()));
                }
                else {
                    Toast.makeText(UserProfile.this, responseUserStats.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse<UserStats>> call, Throwable t) {
                Toast.makeText(UserProfile.this,
                        "Error: ", Toast.LENGTH_LONG).show();
                Log.d("userStatsCallTag", t.getMessage());
            }
        });

    }

    private void userFriendsCall(){
        String token = sp.getString("token", "");

        //Gson gson = new Gson();
        MainApplication.apiManager.getFriends(token, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();

                if (response.isSuccessful() && responseFriends != null) {
                    friendsList.addAll(responseFriends.getData());

                    adapter.setFriends(friendsList);

                    friendsRecView = findViewById(R.id.friendsRecyclerView);
                    friendsRecView.setAdapter(adapter);
                    friendsRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)); //lists data horizontally

                }
                else {
                    Toast.makeText(UserProfile.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponseList<Friend>> call, Throwable t) {
                Toast.makeText(UserProfile.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());

            }
        });

    }

    @Override
    public void onFriendItemClick(int position) {

        Intent intent = new Intent(UserProfile.this, FriendProfile.class);

        intent.putExtra("NAME", friendsList.get(position).getName());
        intent.putExtra("EMAIL", friendsList.get(position).getEmail());
        intent.putExtra("IMAGE", friendsList.get(position).getImageURL());

        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                super.finish();
                break;
            case R.id.logOutBtn:
                SharedPreferences.Editor editor = sp.edit();
                editor.clear(); //clear all shared preferences
                editor.apply(); //apply these changes before going to login page

                Intent intent = new Intent(this, MainActivity.class);
                //ensure that page history is reset before going back to login
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Toast.makeText(UserProfile.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.friendsViewAllBtn:
                startActivity(new Intent(this, FriendsListPage.class));
                break;
            case R.id.settingsBtn:
                startActivity(new Intent(this, Settings.class));
                break;
        }
    }
}