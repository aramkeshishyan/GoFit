package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.gofit.data.model.requests.Challenges.ChallengeDto;
import com.example.gofit.data.model.requests.Challenges.ChallengeRequestDto;
import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.recyclerViews.ChallengeRequestersRecViewAdapter;
import com.example.gofit.recyclerViews.RequestersRecViewAdapter;

import java.util.ArrayList;

public class ChallengeRequestsPage extends AppCompatActivity implements View.OnClickListener, ChallengeRequestersRecViewAdapter.OnChallengeRequestActionListener {

    private ImageButton backBtn;
    private SharedPreferences sp;

    private ArrayList<ChallengeRequestDto> requestersArray = new ArrayList<>();
    private ChallengeRequestersRecViewAdapter requestsAdapter = new ChallengeRequestersRecViewAdapter(this,this);
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

//        Exercise_Item testExItem1 = new Exercise_Item("1", "t", "g", "l", "d","p","t");
//        Exercise_Item testExItem2 = new Exercise_Item("1", "t", "g", "l", "d","p","t");
//        Exercise_Item testExItem3 = new Exercise_Item("1", "t", "g", "l", "d","p","t");
//
//        ArrayList<Exercise_Item> testExList = new ArrayList<>();
//        testExList.add(testExItem1);
//        testExList.add(testExItem2);
//        testExList.add(testExItem3);
//
//        ChallengeDto testChallengeDto = new ChallengeDto(1, "testEmail", "testTitle", "testDescr",testExList, 30,8,3 );
//
//        ChallengeRequestDto testChal = new ChallengeRequestDto(5,testChallengeDto,"crName", "crPhotoUrl");

        //requestersArray.add(testChal);
        //requestsAdapter.setChallengers(requestersArray);


        //requestersRecyclerView.setAdapter(requestsAdapter);
        //requestersRecyclerView.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    public void onChallengeRequestAccepted(ChallengeRequestDto request) {

    }

    @Override
    public void onChallengeRequestDenied(ChallengeRequestDto request) {

    }
}