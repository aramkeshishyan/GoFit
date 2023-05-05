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
import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.Challenges.ChallengeRequestDto;
import com.example.gofit.data.model.requests.ObjectId;
import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.responses.defaultResponse;
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
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        ObjectId challengeIdObject = new ObjectId(request.getRequestId());

//        Toast.makeText(ChallengeRequestsPage.this,
//                String.format("%d", request.getRequestId())
//                , Toast.LENGTH_LONG).show();

        MainApplication.apiManager.acceptChallengeRequest(token, challengeIdObject, new Callback<defaultResponse<ChallengeRecordDto>>() {
            @Override
            public void onResponse(Call<defaultResponse<ChallengeRecordDto>> call, Response<defaultResponse<ChallengeRecordDto>> response) {

            }

            @Override
            public void onFailure(Call<defaultResponse<ChallengeRecordDto>> call, Throwable t) {

            }
        });

        requestersArray.remove(request);            //remove request after accepting
        requestsAdapter.notifyDataSetChanged();     //Update adapter to reflect request removal


    }

    @Override
    public void onChallengeRequestDenied(ChallengeRequestDto request) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        ObjectId challengeIdObject = new ObjectId(request.getRequestId());

        MainApplication.apiManager.denyChallengeRequest(token, challengeIdObject, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {

            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {

            }
        });

        requestersArray.remove(request);
        requestsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onChallengeRequestItemClick(int position) {
        Intent intent = new Intent(ChallengeRequestsPage.this, ChallengeRequestDetailsPage.class);

        intent.putExtra("chal_request_id", requestersArray.get(position).getRequestId());
        intent.putExtra("chal_id", requestersArray.get(position).getChallenge().getChallengeId());
        intent.putExtra("chal_creator_email", requestersArray.get(position).getChallenge().getCreatorEmail());
        intent.putExtra("chal_title", requestersArray.get(position).getChallenge().getTitle());
        intent.putExtra("chal_desc", requestersArray.get(position).getChallenge().getDesc());

        Bundle exerciseArgs = new Bundle();
        exerciseArgs.putSerializable("chal_exerciselist", requestersArray.get(position).getChallenge().getExerciseList());
        intent.putExtras(exerciseArgs);

        intent.putExtra("chal_duration", requestersArray.get(position).getChallenge().getDurationDays());
        intent.putExtra("chal_reps", requestersArray.get(position).getChallenge().getReps());
        intent.putExtra("chal_sets", requestersArray.get(position).getChallenge().getSets());
        intent.putExtra("chal_creator_name", requestersArray.get(position).getCreatorName());
        intent.putExtra("chal_creator_photo", requestersArray.get(position).getCreatorPhotoURL());

        startActivity(intent);

    }
}