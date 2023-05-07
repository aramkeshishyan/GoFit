package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gofit.data.model.requests.Challenges.SendChallengeDto;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.recyclerViews.Challenges.ChallengeRecViewAdapter;
import com.example.gofit.recyclerViews.Challenges.Challenge_ExerciseRV_Adapter;
import com.example.gofit.recyclerViews.ExerciseRecViewAdapter;
import com.example.gofit.recyclerViews.FriendRecyclerViewInterface;
import com.example.gofit.recyclerViews.FriendsRecViewAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeDetails extends AppCompatActivity implements Challenge_ExerciseRV_Adapter.OnNoteListener, View.OnClickListener, FriendRecyclerViewInterface {

    private ImageButton back_Button;
    private Button challengeFriendBtn;
    private ArrayList<Exercise_Item> exercise_list = new ArrayList<>();
    private RecyclerView exercise_recview;
    private Challenge_ExerciseRV_Adapter exercise_adapter;
    private SharedPreferences sp;
    private int challengeId;

    private ArrayList<Friend> friendsList = new ArrayList<>();

    FriendsRecViewAdapter friendsAdapter = new FriendsRecViewAdapter(this,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        getIncomingIntent();
        back_Button = findViewById(R.id.challenge_backbutton);
        back_Button.setOnClickListener(view -> ChallengeDetails.super.finish());

        challengeFriendBtn = findViewById(R.id.ch_send_friend);
        challengeFriendBtn.setOnClickListener(this);
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("title")){
            String title = getIntent().getStringExtra("title");
            int duration = getIntent().getIntExtra("duration", 0);
            int reps = getIntent().getIntExtra("reps", 0);
            int sets = getIntent().getIntExtra("sets", 0);
            int id = getIntent().getIntExtra("id", 0);
            String email = getIntent().getStringExtra("email");
            String description = getIntent().getStringExtra("description");
            exercise_list = (ArrayList<Exercise_Item>) getIntent().getExtras().getSerializable("exercise_list");

            //GET FRIENDS
            friendsList = (ArrayList<Friend>) getIntent().getExtras().getSerializable("friends_list");

            challengeId = getIntent().getIntExtra("id", 0);

            setupItems(title, duration, reps, sets, id, email, description);
        }
    }

    private void setupItems(String title, int duration, int reps, int sets, int id, String email, String description) {

        TextView mTitle = findViewById(R.id.challenge_title);
        mTitle.setText(title);

        TextView mDuration = findViewById(R.id.Challenge_duration);
        mDuration.setText(String.valueOf(duration) + " Days");

        TextView mReps = findViewById(R.id.Challenge_reps);
        mReps.setText(String.valueOf(reps));

        TextView mSets = findViewById(R.id.Challenge_sets);
        mSets.setText(String.valueOf(sets));

        TextView mId = findViewById(R.id.Challenge_id);
        mId.setText(String.valueOf(id));

        TextView mDescription = findViewById(R.id.Challenge_description);
        mDescription.setText(description);

        exercise_adapter = new Challenge_ExerciseRV_Adapter(this, exercise_list, this);
        exercise_adapter.setExercise_List(exercise_list);
        exercise_recview = findViewById(R.id.Challenge_RecView);
        exercise_recview.setAdapter(exercise_adapter);
        exercise_recview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    }
    public void onNoteClick(int position){
        //From Challenge Details page to Exercise Activity page
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("ex_id", exercise_list.get(position).getItem_id());
        intent.putExtra("ex_title", exercise_list.get(position).getItem_name());
        intent.putExtra("ex_group", exercise_list.get(position).getItem_mGroup());
        intent.putExtra("ex_level", exercise_list.get(position).getItem_level());
        intent.putExtra("ex_description", exercise_list.get(position).getItem_description());
        intent.putExtra("ex_imageUrl", exercise_list.get(position).getItem_image());
        intent.putExtra("ex_type", exercise_list.get(position).getItem_type());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ch_send_friend:
                challengeFriendDialogue();
                break;
        }
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

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show() ;
    }

    @Override
    public void onFriendItemClick(int position) {

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
                    Toast.makeText(ChallengeDetails.this, responseSendChal.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChallengeActivity.this, String.format("Challenge %d Completed!", challengeId), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {
                Toast.makeText(ChallengeDetails.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }
}