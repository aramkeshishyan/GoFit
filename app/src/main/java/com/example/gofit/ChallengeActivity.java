package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gofit.recyclerViews.ExerciseRecViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChallengeActivity extends AppCompatActivity {

    private ImageButton backButton;

    private RecyclerView exercise;
    //private ArrayList<Exercise_Item> challengeExercisesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge1);

        getIncomingIntent();
        backButton = findViewById(R.id.chal_backButton);
        backButton.setOnClickListener(view -> ChallengeActivity.super.finish());

    }
    private void getIncomingIntent() {

        if(getIntent().hasExtra("chal_title") && getIntent().hasExtra("chal_description"))
        {
            String title = getIntent().getStringExtra("chal_title");
            String description = getIntent().getStringExtra("chal_description");

            //Bundle args = getIntent().getBundleExtra("BUNDLE");
            //ArrayList<Exercise_Item> challengeExercisesList = (ArrayList<Exercise_Item>) args.getSerializable("ARRAYLIST");
            ArrayList<Exercise_Item> challengeExercisesList = new ArrayList<>();

            String durationDays = getIntent().getStringExtra("chal_durationDays");
            String repetitions = getIntent().getStringExtra("chal_repetitions");
            String sets = getIntent().getStringExtra("chal_sets");
            String creator = getIntent().getStringExtra("chal_creator");
            String dateAccepted = getIntent().getStringExtra("chal_date_accepted");
            String dateStarted = getIntent().getStringExtra("chal_date_started");
            String dateEnd = getIntent().getStringExtra("chal_date_end");
            String dateLastCompleted = getIntent().getStringExtra("chal_date_last_completed");
            String completed = getIntent().getStringExtra("chal_complete");
            String streak = getIntent().getStringExtra("chal_streak");
            String score = getIntent().getStringExtra("chal_score");

            setupItems(title, description, challengeExercisesList, durationDays, repetitions, sets, creator, dateAccepted, dateStarted, dateEnd, dateLastCompleted, completed, streak, score);
        }


    }

    private void setupItems(String title, String description, ArrayList<Exercise_Item> challengeExercisesList, String durationDays, String repetitions, String sets, String creator, String dateAccepted, String dateStarted, String dateEnd, String dateLastCompleted, String completed, String streak, String score) {
        TextView mTitle = findViewById(R.id.ch_title);
        mTitle.setText(title);

        TextView mDurationDays = findViewById(R.id.ch_daysRemaining);
        mDurationDays.setText(durationDays);

        TextView mDescription = findViewById(R.id.ch_description);
        mDescription.setText("Description: " + description);

        //ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(this, challengeExercisesList, this::onNoteClick);
        //exerciseAdapter.setExercises(challengeExercisesList);

        //exercise = findViewById(R.id.ch_exercise_RecView);
        //exercise.setAdapter(exerciseAdapter);
        //exercise.setLayoutManager(new LinearLayoutManager(this));


        TextView mRepetitions = findViewById(R.id.ch_repetitions);
        mRepetitions.setText("Repetitions: " + repetitions);

        TextView mSets = findViewById(R.id.ch_sets);
        mSets.setText("Sets: " + sets);

        //TextView mCreator = findViewById()

        TextView mDateAccepted = findViewById(R.id.ch_date_accepted);
        mDateAccepted.setText("Date Accepted: " + dateAccepted);

        TextView mDateStarted = findViewById(R.id.ch_date_started);
        mDateStarted.setText("Date Started: " + dateStarted);

        TextView mDateEnd = findViewById(R.id.ch_date_ended);
        mDateEnd.setText("Date End: " + dateEnd);

        TextView mDateLastCompleted = findViewById(R.id.ch_date_last_completed);
        mDateLastCompleted.setText("Date Last Completed: " + dateLastCompleted);

        TextView mStreak = findViewById(R.id.ch_streak);
        mStreak.setText("Streak: " + streak);

        TextView mScore = findViewById(R.id.ch_score);
        mScore.setText("Score: " + score);


    }

    private void onNoteClick(int i) {
    }
}