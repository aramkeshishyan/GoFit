package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gofit.recyclerViews.Challenges.Challenge_ExerciseRV_Adapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChallengeRequestDetailsPage extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;

    private RecyclerView exerciseRcV;
    private int chalRequestId, challengeId;
    private String creatorEmail, creatorName, chalTitle, chalDesc, chalCreatorPhoto, chalDuration, chalReps, chalSets;
    private ArrayList<Exercise_Item> challengeExercisesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_request_details_page);

        backBtn = findViewById(R.id.chal_backButton);
        backBtn.setOnClickListener(this);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("chal_title") && getIntent().hasExtra("chal_desc"))
        {
            chalRequestId = getIntent().getIntExtra("chal_request_id", 0);
            challengeId = getIntent().getIntExtra("chal_id",0);
            creatorEmail = getIntent().getStringExtra("chal_creator_email");
            chalTitle = getIntent().getStringExtra("chal_title");
            chalDesc = getIntent().getStringExtra("chal_desc");
            challengeExercisesList = (ArrayList<Exercise_Item>) getIntent().getExtras().getSerializable("chal_exerciselist");


            chalDuration= Integer.toString(getIntent().getIntExtra("chal_duration",0));
            chalReps = Integer.toString(getIntent().getIntExtra("chal_reps",0));
            chalSets = Integer.toString(getIntent().getIntExtra("chal_sets",0));

            creatorName = getIntent().getStringExtra("chal_creator_name");
            chalCreatorPhoto = getIntent().getStringExtra("chal_creator_photo");

            setupItems();
        }
    }

    private void setupItems() {

        TextView mTitle = findViewById(R.id.ch_title);
        mTitle.setText(chalTitle);

        TextView mDuration = findViewById(R.id.ch_duration);
        if(chalDuration == null) {
            chalDuration = "N/A";
        }
        mDuration.setText(chalDuration + " Days");

        TextView mDesc = findViewById(R.id.ch_description);
        mDesc.setText(chalDesc);

        Challenge_ExerciseRV_Adapter exerciseAdapter = new Challenge_ExerciseRV_Adapter(this, challengeExercisesList, this::onNoteClick);
        exerciseAdapter.setExercise_List(challengeExercisesList);

        exerciseRcV = findViewById(R.id.ch_exercise_RecView);
        exerciseRcV.setAdapter(exerciseAdapter);
        exerciseRcV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        TextView mReps = findViewById(R.id.ch_repetitions);
        mReps.setText(chalReps);

        TextView mSets = findViewById(R.id.ch_sets);
        mSets.setText(chalSets);

        ImageView mCreatorPhoto = findViewById(R.id.ch_creator_profileImgV);
        if (chalCreatorPhoto.isEmpty()) {
            chalCreatorPhoto = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }
        Glide.with(this).asBitmap().load(chalCreatorPhoto).centerCrop().into(mCreatorPhoto);

        TextView mCreatorName = findViewById(R.id.ch_creator_name);
        mCreatorName.setText(creatorName);

        TextView mCreatorEmail = findViewById(R.id.ch_creator_email);
        mCreatorEmail.setText(creatorEmail);



    }

    private void onNoteClick(int i) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chal_backButton:
                super.finish();
                break;
        }
    }
}