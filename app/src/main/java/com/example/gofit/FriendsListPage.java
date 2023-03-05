package com.example.gofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gofit.data.model.requests.UserFriended;
import com.example.gofit.data.model.responses.addFriendResponse;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListPage extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView friendsRecyclerView;
    private SearchView searchViewFriendsList;

    private ImageButton btnBackFriendsList;
    private ImageButton btnAddFriend;

    //Fake friends list for testing purposes
    private ArrayList<Friend> friendsList2 = new ArrayList<>();
    private FriendsRecViewAdapter adapter2 = new FriendsRecViewAdapter(this);
    Context context = this;
    private SharedPreferences sp;

    private String friendToAddEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list_page);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        btnBackFriendsList = findViewById(R.id.btnBackFriendsList);
        btnBackFriendsList.setOnClickListener(this);

        btnAddFriend = findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(this);

        friendToAddEmail = "";

        searchViewFriendsList = findViewById(R.id.searchViewFriendsList);
        searchViewFriendsList.clearFocus(); //removes cursor from search view initially
        searchViewFriendsList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        /*for (int i = 0; i < 2; i++) {
            friends2.add(new Friend("John Smith", "johnsmith@gmail.com","https://st.depositphotos.com/1269204/1219/i/600/depositphotos_12196477-stock-photo-smiling-men-isolated-on-the.jpg"));
            friends2.add(new Friend("Jane Doe", "janeDoe@gmail.com","https://image.shutterstock.com/image-photo/indoor-portrait-beautiful-brunette-young-260nw-640005220.jpg"));
            friends2.add(new Friend("Margot Robbie", "margotrobbie@gmail.com","https://assets.vogue.com/photos/5cf7ed4504f90a017a26d60f/master/pass/5-things-to-know-about-margot-robbie.jpg"));
            friends2.add(new Friend("Scarlette Johanson", "scarlJo@gmail.com", "https://m.media-amazon.com/images/M/MV5BMTM3OTUwMDYwNl5BMl5BanBnXkFtZTcwNTUyNzc3Nw@@._V1_UY1200_CR180,0,630,1200_AL_.jpg"));
            friends2.add(new Friend("Ryan Gosling", "ryangos@gmaill.com", "https://upload.wikimedia.org/wikipedia/commons/f/f6/Ryan_Gosling_in_2018.jpg"));
            friends2.add(new Friend("Adam Sandler", "adamSandler@gmail.com", "https://cdn.britannica.com/24/157824-050-D8E9E191/Adam-Sandler-2011.jpg"));
            friends2.add(new Friend("Emma Watson", "emmawatson@gmail.com", "https://upload.wikimedia.org/wikipedia/commons/7/7f/Emma_Watson_2013.jpg"));
            friends2.add(new Friend("Mark Zuckerberg", "markzuck@gmail.com", "https://cdn.britannica.com/99/236599-050-1199AD2C/Mark-Zuckerberg-2019.jpg"));
        }*/

        userFriendsCall();
//        adapter2.setFriends(friendsList2);
//        friendsRecyclerView = findViewById(R.id.friendsListPageRecView);
//        friendsRecyclerView.setAdapter(adapter2);
//        friendsRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        String token = sp.getString("token", "");

        String friendsEmail = "string";
        UserFriended userFriended = new UserFriended(friendsEmail);
        MainApplication.apiManager.addFriend(token, userFriended, new Callback<addFriendResponse>() {
            @Override
            public void onResponse(Call<addFriendResponse> call, Response<addFriendResponse> response) {
                addFriendResponse responseAdd = response.body();

                //if(response.isSuccessful() && !(response.body().getMessage().isEmpty()))
                if(response.isSuccessful() && responseAdd != null)
                {
                    Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.getMessage()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.isSuccess()), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(FriendsListPage.this, "Failed addFriend", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<addFriendResponse> call, Throwable t) {
                Toast.makeText(FriendsListPage.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());

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
                    friendsList2.addAll(responseFriends.getData());

                    //jsonText = gson.toJson(friendsList);
                    //sp.edit().putString("FriendsList", jsonText);
                    //sp.edit().apply();

                    Toast.makeText(FriendsListPage.this,
                            "Get Friends was Successful",
                            Toast.LENGTH_SHORT).show();

                    adapter2.setFriends(friendsList2);

                    friendsRecyclerView = findViewById(R.id.friendsListPageRecView);
                    friendsRecyclerView.setAdapter(adapter2);
                    friendsRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));

                }
                else {
                    Toast.makeText(FriendsListPage.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponseList<Friend>> call, Throwable t) {
                Toast.makeText(FriendsListPage.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());

            }
        });

    }




    private void filterList(String s) { //s is text searched in searchView
        ArrayList<Friend> filteredFriendsList = new ArrayList<>();
        for (Friend friend : friendsList2) {
            if (friend.getName().toLowerCase().contains(s.toLowerCase())) {
                filteredFriendsList.add(friend);
            }
        }

        if (filteredFriendsList.isEmpty()) {
            Toast.makeText(this, "No friends found.", Toast.LENGTH_SHORT).show();
        } else {
            adapter2.setFriends(filteredFriendsList); //update data in recyclerView to show searched data.
        }
    }

    private void addFriendCall(){




//        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListPage.this);
//        builder.setTitle("Enter user email");
//
//        EditText friendInput = new EditText(FriendsListPage.this);
//        friendInput.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(friendInput);
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                friendToAddEmail = friendInput.getText().toString().trim();
//                if(!friendToAddEmail.isEmpty()) {
//                    Toast.makeText(FriendsListPage.this, String.format("%s", friendToAddEmail), Toast.LENGTH_SHORT).show();
//                    MainApplication.apiManager.addFriend(token, friendToAddEmail, new Callback<defaultResponse<Boolean>>() {
//                        @Override
//                        public void onResponse(Call<defaultResponse<Boolean>> call, Response<defaultResponse<Boolean>> response) {
//                            defaultResponse<Boolean> responseAdd = response.body();
//
//                            if(response.body() != null)
//                                Toast.makeText(FriendsListPage.this, String.format("%s", response.body().getMessage()), Toast.LENGTH_SHORT).show();
//
////                            if(responseAdd != null && response.isSuccessful())
////                            {
////                                Toast.makeText(FriendsListPage.this, "Request Sent", Toast.LENGTH_SHORT).show();
////                            }
////                            else
////                                //Toast.makeText(FriendsListPage.this, "Failed to add friend", Toast.LENGTH_SHORT).show();
////                            {
////                                assert responseAdd != null;
////                                Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.getMessage()), Toast.LENGTH_SHORT).show();
////                            }
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<defaultResponse<Boolean>> call, Throwable t) {
//                            Toast.makeText(FriendsListPage.this,
//                                    "Error: " + t.getMessage()
//                                    , Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//
//                    friendToAddEmail = "";
//                }
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackFriendsList:
                super.finish();
                break;
            case R.id.btnAddFriend:
                addFriendCall();
                break;
        }
    }
}