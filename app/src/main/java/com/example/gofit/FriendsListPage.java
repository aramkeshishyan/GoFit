package com.example.gofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gofit.data.model.requests.UserFriended;
import com.example.gofit.data.model.responses.addFriendResponse;
import com.example.gofit.data.model.responses.defaultResponseList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListPage extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView friendsRecyclerView;
    private SearchView searchViewFriendsList;

    private ImageButton btnBackFriendsList;
    private ImageButton btnAddFriend;

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
                    Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.getMessage()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.isSuccess()), Toast.LENGTH_SHORT).show();
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