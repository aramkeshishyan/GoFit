package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.requests.UserAcceptedDenied;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.RequestersRecViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestsPage extends AppCompatActivity implements View.OnClickListener, RequestersRecViewAdapter.OnFriendRequestActionListener {

    private ImageButton backBtn;
    private SharedPreferences sp;

    private ArrayList<RequestersInfo> requestersArray = new ArrayList<>();
    private RequestersRecViewAdapter requestsAdapter = new RequestersRecViewAdapter(this,this);
    private RecyclerView requestersRecyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests_page);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        backBtn = findViewById(R.id.btnBackFriendRequests);
        backBtn.setOnClickListener(this);


        friendRequestsCall();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackFriendRequests:
                super.finish();
//                startActivity(new Intent(this, FriendsListPage.class));
                break;
        }
    }

    private void friendRequestsCall() {
        String token = sp.getString("token", "");
        MainApplication.apiManager.getFriendRequests(token, new Callback<defaultResponseList<RequestersInfo>>() {
            @Override
            public void onResponse(Call<defaultResponseList<RequestersInfo>> call, Response<defaultResponseList<RequestersInfo>> response) {
                defaultResponseList<RequestersInfo> requestersList = response.body();

                if(requestersList.getData() == null)
                {
                    Toast.makeText(FriendRequestsPage.this,
                            "No requests",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    requestersArray.addAll(requestersList.getData());
                    requestsAdapter.setFriends(requestersArray);

                    requestersRecyclerView = findViewById(R.id.requestersPageRecView);
                    requestersRecyclerView.setAdapter(requestsAdapter);
                    requestersRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                    /*for(int i = 0; i < requestersArray.size(); i++)
                    {
                        Toast.makeText(FriendsListPage.this,
                                String.format("Requester %d is %s", i, requestersArray.get(i).getFullName())
                                , Toast.LENGTH_LONG).show();
                    }*/
                }

            }

            @Override
            public void onFailure(Call<defaultResponseList<RequestersInfo>> call, Throwable t) {
                Toast.makeText(FriendRequestsPage.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });


    }

    //Methods to Accept/Deny friend requests
    @Override
    public void onFriendRequestAccepted(RequestersInfo request) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        UserAcceptedDenied acceptedUser = new UserAcceptedDenied(request.getRequestId());

        MainApplication.apiManager.acceptFriend(token, acceptedUser, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();
            }

            @Override
            public void onFailure(Call<defaultResponseList<Friend>> call, Throwable t) {

            }
        });

        requestersArray.remove(request);            //remove request after accepting
        requestsAdapter.notifyDataSetChanged();     //Update adapter to reflect request removal
    }

    @Override
    public void onFriendRequestDenied(RequestersInfo request) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        UserAcceptedDenied deniedUser = new UserAcceptedDenied(request.getRequestId());

        MainApplication.apiManager.denyFriend(token, deniedUser, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {
                defaultResponse<String> denyResponse = response.body();
            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {

            }
        });

        requestersArray.remove(request);
        requestsAdapter.notifyDataSetChanged();
    }
}