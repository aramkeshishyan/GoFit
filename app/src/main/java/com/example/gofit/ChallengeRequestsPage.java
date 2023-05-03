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

import com.example.gofit.data.model.requests.Challenges.ChallengeDto;
import com.example.gofit.data.model.requests.Challenges.ChallengeRequestDto;
import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.ChallengeRequestersRecViewAdapter;
import com.example.gofit.recyclerViews.RequestersRecViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeRequestsPage extends AppCompatActivity implements View.OnClickListener, ChallengeRequestersRecViewAdapter.OnChallengeRequestActionListener, ChallengeRequestRecyclerViewInterface {

    private ImageButton backBtn;
    private SharedPreferences sp;

    private ArrayList<ChallengeRequestDto> requestersArray = new ArrayList<>();
    private ChallengeRequestersRecViewAdapter requestsAdapter = new ChallengeRequestersRecViewAdapter(this,this, this);
    private RecyclerView requestersRecyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_requests_page);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        backBtn = findViewById(R.id.btnBackChallengeRequests);
        backBtn.setOnClickListener(this);

        requestersRecyclerView = findViewById(R.id.challengeRequestsRecView);
        
        challengeRequestsCall();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackChallengeRequests:
                super.finish();
                break;
        }
    }

    private void challengeRequestsCall() {

        String token = sp.getString("token", "");

        MainApplication.apiManager.getUserChallengeRequests(token, new Callback<defaultResponseList<ChallengeRequestDto>>() {
            @Override
            public void onResponse(Call<defaultResponseList<ChallengeRequestDto>> call, Response<defaultResponseList<ChallengeRequestDto>> response) {
                defaultResponseList<ChallengeRequestDto> requestersList = response.body();

                if(requestersList.getData() == null)
                {
                    Toast.makeText(ChallengeRequestsPage.this,
                            "No Challenge Requests",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    requestersArray.addAll(requestersList.getData());
                    requestsAdapter.setChallengers(requestersArray);

                    requestersRecyclerView.setAdapter(requestsAdapter);
                    requestersRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                }
            }

            @Override
            public void onFailure(Call<defaultResponseList<ChallengeRequestDto>> call, Throwable t) {
                Toast.makeText(ChallengeRequestsPage.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onChallengeRequestAccepted(ChallengeRequestDto request) {

    }

    @Override
    public void onChallengeRequestDenied(ChallengeRequestDto request) {

    }

    @Override
    public void onChallengeRequestItemClick(int position) {
        Intent intent = new Intent(ChallengeRequestsPage.this, ChallengeRequestDetailsPage.class);
        startActivity(intent);

    }
}