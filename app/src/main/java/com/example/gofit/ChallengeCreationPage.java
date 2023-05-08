package com.example.gofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.Challenges.CreateChallengeDto;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.recyclerViews.ChallengeCreateAdapter;
import com.example.gofit.recyclerViews.Challenges.Challenge_ExerciseRV_Adapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeCreationPage extends AppCompatActivity {
    private ImageButton backButton;
    private Button cancelButton;
    private Button addExerciseButton;
    private Button createButton;
    private ArrayList<Exercise_Item> exerciseList = new ArrayList<>();
    private RecyclerView exerciseRecView;
    private ChallengeCreateAdapter adapter;
    private TextView noExercises;

    //Challenge Attributes
    private String chalCreatorEmail;
    private String chalTitle;
    private String chalDescription;
    private ArrayList<Integer> chalExercisesIds = new ArrayList<>();
    private int chalDuration;
    private int chalReps;
    private int chalSets;
    private TextInputEditText title, description, duration, reps, sets;
    private SharedPreferences sp ;
    private final static int MY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_creation_page);

        sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        backButton = findViewById(R.id.cc_backButton);
        backButton.setOnClickListener(view -> ChallengeCreationPage.super.finish());
        cancelButton = findViewById(R.id.cc_cancelButton);
        cancelButton.setOnClickListener(view -> ChallengeCreationPage.super.finish());

        exerciseRecView = findViewById(R.id.cc_recview);
        noExercises = findViewById(R.id.cc_exerciseTextView);

        if(exerciseList.isEmpty()){
            exerciseRecView.setVisibility(View.GONE);
            noExercises.setVisibility(View.VISIBLE);
        }
        else{
            exerciseRecView.setVisibility(View.VISIBLE);
            noExercises.setVisibility(View.GONE);
        }

        adapter = new ChallengeCreateAdapter(this, exerciseList);
        adapter.setExercise(exerciseList);
        exerciseRecView.setAdapter(adapter);
        exerciseRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.notifyDataSetChanged();

        addExerciseButton = findViewById(R.id.cc_addButton);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChallengeCreationPage.this, ChallengeSelectExercise.class);
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });

        createButton = findViewById(R.id.cc_createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChallengeCheck();
            }
        });

    }

    private void createChallengeCheck() {

        chalCreatorEmail = sp.getString("email", "");
        title = findViewById(R.id.cc_title);
        chalTitle = title.getText().toString().trim();

        description = findViewById(R.id.cc_description);
        chalDescription = description.getText().toString().trim();

        duration = findViewById(R.id.cc_duration);
        duration.setInputType(InputType.TYPE_CLASS_NUMBER);
        try{
        chalDuration = Integer.parseInt(duration.getText().toString().trim());
        }
        catch(NumberFormatException e){
            duration.setError("Duration cannot be Empty");
            duration.requestFocus();
            return;
        }

        reps = findViewById(R.id.cc_repetitions);
        reps.setInputType(InputType.TYPE_CLASS_NUMBER);
        try{
            chalReps = Integer.parseInt(reps.getText().toString().trim());
        }
        catch(NumberFormatException e){
            reps.setError("Repetitions cannot be Empty");
            reps.requestFocus();
            return;
        }

        sets = findViewById(R.id.cc_sets);
        sets.setInputType(InputType.TYPE_CLASS_NUMBER);
        try{
            chalSets = Integer.parseInt(sets.getText().toString().trim());
        }
        catch(NumberFormatException e){
            sets.setError("Sets amount cannot be Empty");
            sets.requestFocus();
            return;
        }
        for (int i = 0; i < exerciseList.size(); i++) {
            chalExercisesIds.add(Integer.parseInt(exerciseList.get(i).getItem_id().trim()));
        }

        if(chalTitle.isEmpty()){
            title.setError("Title is Required");
            title.requestFocus();
            return;
        }
        if(chalDescription.isEmpty()){
            description.setError("Description is Required");
            description.requestFocus();
            return;
        }
        if(chalDuration == 0){
            duration.setError("Duration cannot be 0");
            duration.requestFocus();
            return;
        }
        if(chalReps == 0){
            reps.setError("Repetitions cannot be 0");
            reps.requestFocus();
            return;
        }
        if(chalSets == 0){
            sets.setError("Repetitions cannot be 0");
            sets.requestFocus();
            return;
        }
        createChallengeCall();
        ChallengeCreationPage.super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MY_REQUEST_CODE) {
                if (data != null) {
                    exerciseList = (ArrayList<Exercise_Item>) data.getSerializableExtra("exerciseList");
                    //exerciseList = (ArrayList<Exercise_Item>) getIntent().getExtras().getSerializable("exerciseList");
                    adapter.setExercise(exerciseList);
                    exerciseRecView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    if(exerciseList.isEmpty()){
                        exerciseRecView.setVisibility(View.GONE);
                        noExercises.setVisibility(View.VISIBLE);
                    }
                    else{
                        exerciseRecView.setVisibility(View.VISIBLE);
                        noExercises.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
    private void createChallengeCall () {

        String token = sp.getString("token", "");

        //CreateChallengeDto testModel = new CreateChallengeDto(userEmail, "Test Challenge", "Test Description", chalExercisesIds,10, 8,3);
        CreateChallengeDto testModel = new CreateChallengeDto(chalCreatorEmail, chalTitle, chalDescription, chalExercisesIds,chalDuration, chalReps,chalSets);
        MainApplication.apiManager.createChallenge(token, testModel, new Callback<defaultResponse<ChallengeRecordDto>>() {
            @Override
            public void onResponse(Call<defaultResponse<ChallengeRecordDto>> call, Response<defaultResponse<ChallengeRecordDto>> response) {
                defaultResponse<ChallengeRecordDto> challengeRecords = response.body();


                //custom_challenges.add(challengeRecords.getData()) ;

                /////reset the UI data  /////////

                //adapter2.setChallengesList(custom_challenges);
                //challenge_r.setAdapter(adapter2);

                ////////////////////////////////

                //Toast.makeText(getActivity(),
                //        "Challenge Creation Successful",
                //        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<defaultResponse<ChallengeRecordDto>> call, Throwable t) {

            }
        });
    }
}