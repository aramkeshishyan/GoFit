package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gofit.recyclerViews.Challenges.ChallengeRecViewAdapter;
import com.example.gofit.recyclerViews.Challenges.Challenge_ExerciseRV_Adapter;
import com.example.gofit.recyclerViews.ExerciseRecViewAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class ChallengeDetails extends AppCompatActivity implements Challenge_ExerciseRV_Adapter.OnNoteListener{

    private ImageButton back_Button;
    private ArrayList<Exercise_Item> exercise_list = new ArrayList<>();
    private RecyclerView exercise_recview;
    private Challenge_ExerciseRV_Adapter exercise_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);
        getIncomingIntent();
        back_Button = findViewById(R.id.challenge_backbutton);
        back_Button.setOnClickListener(view -> ChallengeDetails.super.finish());
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

            setupItems(title, duration, reps, sets, id, email, description);
        }
    }

    private void setupItems(String title, int duration, int reps, int sets, int id, String email, String description) {

        TextView mTitle = findViewById(R.id.challenge_title);
        mTitle.setText(title);

        TextView mDuration = findViewById(R.id.Challenge_duration);
        mDuration.setText("Challenge Duration: " + String.valueOf(duration) + " days");

        TextView mReps = findViewById(R.id.Challenge_reps);
        mReps.setText("# of Repititions: " + String.valueOf(reps));

        TextView mSets = findViewById(R.id.Challenge_sets);
        mSets.setText("# of Sets: " + String.valueOf(sets));

        TextView mId = findViewById(R.id.Challenge_id);
        mId.setText("Challenge ID: " + String.valueOf(id));

        TextView mEmail = findViewById(R.id.Challenge_email);
        mEmail.setText("Challenger's email: \n" + email);

        TextView mDescription = findViewById(R.id.Challenge_description);
        mDescription.setText("Challenge Description: \n" + description);

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
}