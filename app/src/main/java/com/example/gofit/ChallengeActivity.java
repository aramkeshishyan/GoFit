package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.Challenges.SendChallengeDto;
import com.example.gofit.data.model.requests.ObjectId;
import com.example.gofit.data.model.requests.UserStats;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.recyclerViews.Challenges.Challenge_ExerciseRV_Adapter;
import com.example.gofit.recyclerViews.ExerciseRecViewAdapter;
import com.example.gofit.recyclerViews.FriendRecyclerViewInterface;
import com.example.gofit.recyclerViews.FriendsRecViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeActivity extends AppCompatActivity implements View.OnClickListener, FriendRecyclerViewInterface {

    private ImageButton backButton;

    private RecyclerView exercise;
    private ArrayList<Exercise_Item> challengeExercisesList = new ArrayList<>();
    private ArrayList<Friend> friendsList = new ArrayList<>();

    FriendsRecViewAdapter friendsAdapter = new FriendsRecViewAdapter(this,this);

    private int challengeId;
    private SharedPreferences sp;
    private Button completeBtn;
    private Button challengeFriendBtn;

    private String splitString[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge1);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        getIncomingIntent();
        backButton = findViewById(R.id.chal_backButton);
        backButton.setOnClickListener(view -> ChallengeActivity.super.finish());

        completeBtn = findViewById(R.id.ch_complete);
        completeBtn.setOnClickListener(this);

        challengeFriendBtn = findViewById(R.id.ch_send_friend);
        challengeFriendBtn.setOnClickListener(this);


    }
    private void getIncomingIntent() {

        if(getIntent().hasExtra("chal_title") && getIntent().hasExtra("chal_description"))
        {
            challengeId = getIntent().getIntExtra("chal_id",0);
            String title = getIntent().getStringExtra("chal_title");
            String description = getIntent().getStringExtra("chal_description");
            challengeExercisesList = (ArrayList<Exercise_Item>) getIntent().getExtras().getSerializable("chal_exerciselist");
            //Bundle args = getIntent().getBundleExtra("BUNDLE");
            //ArrayList<Exercise_Item> challengeExercisesList = (ArrayList<Exercise_Item>) args.getSerializable("ARRAYLIST");
            //ArrayList<Exercise_Item> challengeExercisesList = new ArrayList<>();

            String durationDays = Integer.toString(getIntent().getIntExtra("chal_durationDays",0));
            String repetitions = Integer.toString(getIntent().getIntExtra("chal_repetitions",0));
            String sets = Integer.toString(getIntent().getIntExtra("chal_sets",0));
            String creator = getIntent().getStringExtra("chal_creator");
            String dateAccepted = getIntent().getStringExtra("chal_date_accepted");
            String dateStarted = getIntent().getStringExtra("chal_date_started");
            String dateEnd = getIntent().getStringExtra("chal_date_end");
            String dateLastCompleted = getIntent().getStringExtra("chal_date_last_completed");
            String completed = Boolean.toString(getIntent().getBooleanExtra("chal_complete",false));
            String streak = Integer.toString(getIntent().getIntExtra("chal_streak",0));
            String totalDaysCompleted = Integer.toString(getIntent().getIntExtra("chal_total_days_completed",0));
            String score = Integer.toString(getIntent().getIntExtra("chal_score", 0));

            //GET FRIENDS
            friendsList = (ArrayList<Friend>) getIntent().getExtras().getSerializable("friends_list");


            //Get rid of Times in dates
            splitString = dateAccepted.split("T", 2);
            dateAccepted = splitString[0];

            if (dateStarted != null) {
                splitString = dateStarted.split("T", 2);
                dateStarted = splitString[0];
            }

            if(dateEnd !=null){
                splitString = dateEnd.split("T", 2);
                dateEnd = splitString[0];
            }

            if (dateLastCompleted != null) {
                splitString = dateLastCompleted.split("T", 2);
                dateLastCompleted = splitString[0];
            }

            setupItems(title, description, challengeExercisesList, durationDays, repetitions, sets, creator, dateAccepted, dateStarted, dateEnd, dateLastCompleted, completed, streak, totalDaysCompleted, score);
        }


    }

    private void setupItems(String title, String description, ArrayList<Exercise_Item> challengeExercisesList, String durationDays, String repetitions, String sets, String creator, String dateAccepted, String dateStarted, String dateEnd, String dateLastCompleted, String completed, String streak, String totalDaysCompleted, String score) {

        //Toast.makeText(ChallengeActivity.this, String.format("Complete Status %s", completed), Toast.LENGTH_SHORT).show();


        TextView mTitle = findViewById(R.id.ch_title);
        mTitle.setText(title);

        TextView mDurationDays = findViewById(R.id.ch_daysRemaining);
        if(durationDays == null){
            durationDays = "N/A";
        }
        mDurationDays.setText("Duration: " + durationDays + " days");

        TextView mDescription = findViewById(R.id.ch_description);
        mDescription.setText("Description: " + description);

        Challenge_ExerciseRV_Adapter exerciseAdapter = new Challenge_ExerciseRV_Adapter(this, challengeExercisesList, this::onNoteClick);
        exerciseAdapter.setExercise_List(challengeExercisesList);

        exercise = findViewById(R.id.ch_exercise_RecView);
        exercise.setAdapter(exerciseAdapter);
        exercise.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        TextView mRepetitions = findViewById(R.id.ch_repetitions);
        mRepetitions.setText("Repetitions: " + repetitions);

        TextView mSets = findViewById(R.id.ch_sets);
        mSets.setText("Sets: " + sets);

        //TextView mCreator = findViewById()

        TextView mDateAccepted = findViewById(R.id.ch_date_accepted);
        if(dateAccepted == null){
            dateAccepted = "N/A";
        }
        mDateAccepted.setText("Date Accepted: " + dateAccepted);

        TextView mDateStarted = findViewById(R.id.ch_date_started);
        if(dateStarted == null){
            dateStarted = "N/A";
        }
        mDateStarted.setText("Date Started: " + dateStarted);

        TextView mDateEnd = findViewById(R.id.ch_date_ended);
        if(dateEnd == null){
            dateEnd = "N/A";
        }
        mDateEnd.setText("Date End: " + dateEnd);

        TextView mDateLastCompleted = findViewById(R.id.ch_date_last_completed);
        if(dateLastCompleted == null){
            dateLastCompleted = "N/A";
        }
        mDateLastCompleted.setText("Date Last Completed: " + dateLastCompleted);

        TextView mStreak = findViewById(R.id.ch_streak);
        if(streak == null){
            streak = "0";
        }
        mStreak.setText("Streak: " + streak);

        TextView mTotalDaysCompleted = findViewById(R.id.ch_total_days_completed);
        if(totalDaysCompleted == null){
            totalDaysCompleted = "0";
        }
        mTotalDaysCompleted.setText("Completed " + totalDaysCompleted + "/" + durationDays +" Days!" );

        TextView mScore = findViewById(R.id.ch_score);
        if(score == null){
            score = "0";
        }
        mScore.setText("Score: " + score);

        TextView mCompleted = findViewById(R.id.ch_isComplete);
        if(completed == "true"){
            mCompleted.setText("Challenge Completed!");
            mCompleted.setTextColor(Color.parseColor("#00FF00"));
        }
        else{
            mCompleted.setText("Challenge Still Active!");
            mCompleted.setTextColor(Color.parseColor("#00FF00"));
        }

        TextView mCreator = findViewById(R.id.creator);
        mCreator.setText("Created By: " + creator);


    }

    private void onNoteClick(int position) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("ex_id", challengeExercisesList.get(position).getItem_id());
        intent.putExtra("ex_title", challengeExercisesList.get(position).getItem_name());
        intent.putExtra("ex_group", challengeExercisesList.get(position).getItem_mGroup());
        intent.putExtra("ex_level", challengeExercisesList.get(position).getItem_level());
        intent.putExtra("ex_description", challengeExercisesList.get(position).getItem_description());
        intent.putExtra("ex_imageUrl", challengeExercisesList.get(position).getItem_image());
        intent.putExtra("ex_type", challengeExercisesList.get(position).getItem_type());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ch_complete:
                completeChallengeCall();
                //Toast.makeText(ChallengeActivity.this, Integer.toString(challengeId) , Toast.LENGTH_LONG).show();
                break;
            case R.id.ch_send_friend:
                challengeFriendDialogue();
                break;
        }

    }

    private void completeChallengeCall() {
        String token = sp.getString("token", "");

        ObjectId challengeIdObj = new ObjectId(challengeId);
        //Toast.makeText(ChallengeActivity.this, Integer.toString(challengeIdObj.getId()) , Toast.LENGTH_LONG).show();

        MainApplication.apiManager.completeChallenge(token, challengeIdObj, new Callback<defaultResponse<ChallengeRecordDto>>() {
            @Override
            public void onResponse(Call<defaultResponse<ChallengeRecordDto>> call, Response<defaultResponse<ChallengeRecordDto>> response) {
                defaultResponse<ChallengeRecordDto> responseChallengeComplete = response.body();

                if (response.isSuccessful() && responseChallengeComplete != null)
                {
                    Toast.makeText(ChallengeActivity.this, responseChallengeComplete.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChallengeActivity.this, String.format("Challenge %d Completed!", challengeId), Toast.LENGTH_SHORT).show();
                    closePageAfterComplete();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse<ChallengeRecordDto>> call, Throwable t) {
                Toast.makeText(ChallengeActivity.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        });
    }

    private void challengeFriendDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle("Challenge Your Friend!");
        builder.setMessage("Choose a Friend");

        RecyclerView friendRecView = new RecyclerView(this);


        friendsAdapter.setFriends(friendsList);

        friendRecView.setAdapter(friendsAdapter);
        friendRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        LinearLayout lp = new LinearLayout(this.getBaseContext());
        lp.setOrientation(LinearLayout.VERTICAL);
        lp.addView(friendRecView);

        builder.setView(lp);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        builder.show() ;


    }

    private void closePageAfterComplete() {
        super.finish();
    }

    @Override
    public void onFriendItemClick(int position) {
        //Toast.makeText(ChallengeActivity.this, String.format("%s", friendsList.get(position).getEmail() ), Toast.LENGTH_SHORT).show();
        //ObjectId challengeIdObj = new ObjectId(challengeId);
        //Toast.makeText(ChallengeActivity.this, Integer.toString(challengeIdObj.getId()) , Toast.LENGTH_LONG).show();

        SendChallengeDto sendChallengeDto = new SendChallengeDto(challengeId, friendsList.get(position).getEmail());
        //Toast.makeText(ChallengeActivity.this, String.format("<%s><%s>", sendChallengeDto.getChallengeId(), sendChallengeDto.getFriendEmail() ), Toast.LENGTH_SHORT).show();
        sendChallengeToFriendCall(sendChallengeDto);
    }

    private void sendChallengeToFriendCall(SendChallengeDto sendChallengeDto) {
        String token = sp.getString("token", "");
        MainApplication.apiManager.sendChallengeRequest(token, sendChallengeDto, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {
                defaultResponse<String> responseSendChal = response.body();

                if (response.isSuccessful() && responseSendChal != null)
                {
                    Toast.makeText(ChallengeActivity.this, responseSendChal.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChallengeActivity.this, String.format("Challenge %d Completed!", challengeId), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {
                Toast.makeText(ChallengeActivity.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }


}