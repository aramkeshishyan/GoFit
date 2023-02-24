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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gofit.data.model.requests.Friends;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.data.model.requests.Friends;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageButton logout;
    private ImageButton backBtn;
    private ImageButton settingsBtn;
    private ImageView userProfileImgV;

    //Horizontal scrolling friends list
    private RecyclerView friendsRecView;
    private Button friendsViewAllBtn;

    private Button getFriendsBtn;

    private SharedPreferences sp;

    private ArrayList<Friend> friendsList = new ArrayList<>();
//    Gson gson = new Gson();
//    String jsonText;

    //sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
    //String token = sp.getString("token", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(this);

        //opens friends list page activity
        friendsViewAllBtn = findViewById(R.id.friendsViewAllBtn);
        friendsViewAllBtn.setOnClickListener(this);

        //testing authorized getFriends API endpoint
        getFriendsBtn = findViewById(R.id.getFriendsBtn);
        getFriendsBtn.setOnClickListener(this);

        //logout button functionality
        logout = (ImageButton) findViewById(R.id.logOutBtn);
        logout.setOnClickListener(this);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);


        //Temporary user profile placeholder
        userProfileImgV = findViewById(R.id.userProfileImgV);
        Glide.with(this)
                .asBitmap()
                .load("https://thumbs.dreamstime.com/b/default-profile-picture-avatar-photo-placeholder-vector-illustration-default-profile-picture-avatar-photo-placeholder-vector-189495158.jpg")
                .centerCrop()
                .into(userProfileImgV);


        userFriendsCall();
    //ArrayList<Friends> friends = new ArrayList<>();

    //jsonText = sp.getString("FriendsList", null);
    //Type type = new TypeToken<ArrayList<Friends>>() {}.getType();
    //friendsList = gson.fromJson(jsonText, type);


        /*for (int i = 0; i < 2; i++) {
            friends.add(new Friend("John Smith", "https://st.depositphotos.com/1269204/1219/i/600/depositphotos_12196477-stock-photo-smiling-men-isolated-on-the.jpg"));
            friends.add(new Friend("Jane Doe", "https://image.shutterstock.com/image-photo/indoor-portrait-beautiful-brunette-young-260nw-640005220.jpg"));
            friends.add(new Friend("Margot Robbie", "https://assets.vogue.com/photos/5cf7ed4504f90a017a26d60f/master/pass/5-things-to-know-about-margot-robbie.jpg"));
            friends.add(new Friend("Scarlette Johanson", "https://m.media-amazon.com/images/M/MV5BMTM3OTUwMDYwNl5BMl5BanBnXkFtZTcwNTUyNzc3Nw@@._V1_UY1200_CR180,0,630,1200_AL_.jpg"));
            friends.add(new Friend("Ryan Gosling", "https://upload.wikimedia.org/wikipedia/commons/f/f6/Ryan_Gosling_in_2018.jpg"));
            friends.add(new Friend("Adam Sandler", "https://cdn.britannica.com/24/157824-050-D8E9E191/Adam-Sandler-2011.jpg"));
            friends.add(new Friend("Emma Watson", "https://upload.wikimedia.org/wikipedia/commons/7/7f/Emma_Watson_2013.jpg"));
            friends.add(new Friend("Mark Zuckerberg", "https://cdn.britannica.com/99/236599-050-1199AD2C/Mark-Zuckerberg-2019.jpg"));
        }*/

    //Adapter binds data from friends array to views in Recycler View
    FriendsRecViewAdapter adapter = new FriendsRecViewAdapter(this);
    adapter.setFriends(friendsList);

    friendsRecView = findViewById(R.id.friendsRecyclerView);
    friendsRecView.setAdapter(adapter);
    friendsRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); //lists data horizontally
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                super.finish();
                break;
            case R.id.logOutBtn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserProfile.this, MainActivity.class));
                Toast.makeText(UserProfile.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.friendsViewAllBtn:
                startActivity(new Intent(this, FriendsListPage.class));
                break;
            case R.id.settingsBtn:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.getFriendsBtn:
                //getUserFriends();
                break;
        }
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

                    //jsonText = gson.toJson(friendsList);
                    //sp.edit().putString("FriendsList", jsonText);
                    //sp.edit().apply();

                    Toast.makeText(UserProfile.this,
                            "Get Friends was Successful",
                            Toast.LENGTH_SHORT).show();

                    for(int i = 0; i < friendsList.size(); i++)
                    {
                        Toast.makeText(UserProfile.this,
                                String.format("%s", friendsList.get(i).getName())
                                , Toast.LENGTH_LONG).show();

                    }





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

}



//    //get users fullname to display under the avatar//////////////
//    final TextView fullNameTextView = (TextView) findViewById(R.id.userFullName);
//
//        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot snapshot) {
//        //Creating a User.java object
//        User userProfile = snapshot.getValue(User.class);
//
//        if(userProfile != null){
//        String fullName = userProfile.fullName;
//        fullNameTextView.setText(fullName);
//        }
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError error) {
//        Toast.makeText(UserProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
//
//        }
//        });
////////////////////////////////////////////////////////////