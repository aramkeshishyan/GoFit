package com.example.gofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.requests.UserFriendedDeleted;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.FriendRecyclerViewInterface;
import com.example.gofit.recyclerViews.FriendsRecViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListPage extends AppCompatActivity implements View.OnClickListener, FriendRecyclerViewInterface {

    private SearchView searchViewFriendsList;

    private ImageButton btnBackFriendsList;
    private ImageButton btnAddFriend;
    private ImageButton btnFriendRequests;

    private RecyclerView friendsRecyclerView;
    private FriendsRecViewAdapter friendsAdapter = new FriendsRecViewAdapter(this,this);
    private ArrayList<Friend> friendsList = new ArrayList<>();
    private ArrayList<Friend> friendsList2 = new ArrayList<>();

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

        btnFriendRequests = findViewById(R.id.btnFrndRequests);
        btnFriendRequests.setOnClickListener(this);

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
        friendRequestsCall();
    }

    //When page is returned to after user clicks back
    @Override
    protected void onRestart() {
        super.onRestart();

        userFriendsCall();
        friendRequestsCall();
    }

    private void friendRequestsCall() {
        String token = sp.getString("token", "");
        MainApplication.apiManager.getFriendRequests(token, new Callback<defaultResponseList<RequestersInfo>>() {
            @Override
            public void onResponse(Call<defaultResponseList<RequestersInfo>> call, Response<defaultResponseList<RequestersInfo>> response) {
                defaultResponseList<RequestersInfo> requestersList = response.body();

                if(response.isSuccessful() && requestersList.getData() != null)
                {
                    //btnFriendRequests.setBackgroundColor(Color.parseColor("#db0d0d"));
                    btnFriendRequests.setColorFilter(Color.parseColor("#db0d0d"));
                }
                else {
                    btnFriendRequests.clearColorFilter();
                }
            }
            @Override
            public void onFailure(Call<defaultResponseList<RequestersInfo>> call, Throwable t) {
                Toast.makeText(FriendsListPage.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }

    //Makes a API call to get the friends of the user and displays them using recycler view
    private void userFriendsCall(){
        String token = sp.getString("token", "");
        MainApplication.apiManager.getFriends(token, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();

                if (response.isSuccessful() && responseFriends != null) {
                    ArrayList<Friend> tempList = new ArrayList<>();
                    tempList.addAll(responseFriends.getData());

                   friendsList = new ArrayList<>(tempList);

//                    Toast.makeText(FriendsListPage.this,
//                            "Get Friends was Successful",
//                            Toast.LENGTH_SHORT).show();

                    friendsAdapter.setFriends(friendsList);

                    friendsRecyclerView = findViewById(R.id.friendsListPageRecView);
                    friendsRecyclerView.setAdapter(friendsAdapter);
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

    private void addFriendCall(){

        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListPage.this);
        builder.setTitle("Enter user email");
        String token = sp.getString("token", "");

        EditText friendInput = new EditText(FriendsListPage.this);
        friendInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(friendInput);
//
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                friendToAddEmail = friendInput.getText().toString().trim();
                if(!friendToAddEmail.isEmpty()) {
                    UserFriendedDeleted userFriended = new UserFriendedDeleted(friendToAddEmail);
                    MainApplication.apiManager.addFriend(token, userFriended, new Callback<defaultResponse<Boolean>>() {
                        @Override
                        public void onResponse(Call<defaultResponse<Boolean>> call, Response<defaultResponse<Boolean>> response) {
                            defaultResponse<Boolean> responseAdd = response.body();

                            if(response.isSuccessful() && responseAdd != null)
                            {
                                Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.getMessage()), Toast.LENGTH_SHORT).show();
                                Toast.makeText(FriendsListPage.this, String.format("%s", responseAdd.isSuccess()), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<defaultResponse<Boolean>> call, Throwable t) {
                            Toast.makeText(FriendsListPage.this,
                                    "Error: " + t.getMessage()
                                    , Toast.LENGTH_LONG).show();

                        }
                    });
                    friendToAddEmail = "";
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void filterList(String s) { //s is text searched in searchView
        ArrayList<Friend> filteredFriendsList = new ArrayList<>();
        for (Friend friend : friendsList) {
            if (friend.getName().toLowerCase().contains(s.toLowerCase())) {
                filteredFriendsList.add(friend);
            }
        }

        if (filteredFriendsList.isEmpty()) {
            Toast.makeText(this, "Friend's list is empty.", Toast.LENGTH_SHORT).show();
        } else {
            FriendsRecViewAdapter newFriendsAdapter = new FriendsRecViewAdapter(this,this);
            newFriendsAdapter.setFriends(filteredFriendsList);
            friendsRecyclerView.setAdapter(newFriendsAdapter);

            friendsList2 = filteredFriendsList;
        }
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
            case R.id.btnFrndRequests:
                startActivity(new Intent(this, FriendRequestsPage.class));
                break;
        }
    }

    //Method to go to any Friend's Profile on click
    @Override
    public void onFriendItemClick(int position) {
        Intent intent = new Intent(FriendsListPage.this, FriendProfile.class);

        if (friendsList2.isEmpty()) {
            intent.putExtra("NAME", friendsList.get(position).getName());
            intent.putExtra("EMAIL", friendsList.get(position).getEmail());
            intent.putExtra("IMAGE", friendsList.get(position).getImageURL());
        }
        else {
            intent.putExtra("NAME", friendsList2.get(position).getName());
            intent.putExtra("EMAIL", friendsList2.get(position).getEmail());
            intent.putExtra("IMAGE", friendsList2.get(position).getImageURL());
        }

        startActivity(intent);

    }
}