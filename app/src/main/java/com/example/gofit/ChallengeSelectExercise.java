package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gofit.data.model.responses.defaultResponseList;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeSelectExercise extends AppCompatActivity implements ChallengeSelectAdapter.OnNoteListener {
    private ImageButton backButton;
    private ArrayList<Exercise_Item> exerciseList = new ArrayList<>();
    private ArrayList<Exercise_Item> exerciseList2 = new ArrayList<>();
    private RecyclerView exerciseRecView;
    private ChallengeSelectAdapter adapter;
    private Button addbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_select_exercise);

        backButton = findViewById(R.id.cl_backButton);
        backButton.setOnClickListener(view -> ChallengeSelectExercise.super.finish());
        addbutton = findViewById(R.id.cl_addButton);



        exercisesCall();
        //exerciseList.add(new Exercise_Item("E1_C", "Crunches", "Core", "Hard", "Test",  "https://betterme.world/articles/wp-content/uploads/2020/10/How-Many-Calories-Do-You-Burn-Doing-Crunches.jpg", "Cardio", false));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        adapter = new ChallengeSelectAdapter(exerciseList, this, this);
        adapter.setExercise_list(exerciseList);
        exerciseRecView = findViewById(R.id.cl_recview);
        exerciseRecView.setAdapter(adapter);
        exerciseRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Exercise_Item item : exerciseList) {
                    if(item.getItem_isSelected().equals(true)){
                        exerciseList2.add(item);
                    }
                }
                Intent intent = new Intent();
                Bundle exerciseArgs = new Bundle();
                exerciseArgs.putSerializable("exerciseList", exerciseList2);
                intent.putExtras(exerciseArgs);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void exercisesCall(){
        MainApplication.apiManager.getExercises(new Callback<defaultResponseList<Exercise_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Exercise_Item>> call, Response<defaultResponseList<Exercise_Item>> response) {
                defaultResponseList<Exercise_Item> responseExercises = response.body();

                if (response.isSuccessful() && responseExercises != null) {
                    exerciseList = new ArrayList<>();
                    exerciseList.addAll(responseExercises.getData());
                    for (Exercise_Item item : exerciseList) {
                        item.setItem_isSelected(false);
                    }
                    adapter.setExercise_list(exerciseList);
                    exerciseRecView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(ChallengeSelectExercise.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<defaultResponseList<Exercise_Item>> call, Throwable t) {
                Toast.makeText(ChallengeSelectExercise.this,
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onNoteClick(int position) {

        //Toggles the CheckBox
        CheckBox checkBox = exerciseRecView.findViewHolderForAdapterPosition(position)
                .itemView.findViewById(R.id.ex_list_checkbox);
        checkBox.setChecked(!checkBox.isChecked());
        //Changes the boolean to true/false for isChecked for the item that is clicked
        if (exerciseList.get(position).getItem_isSelected().equals(false)) {
            exerciseList.get(position).setItem_isSelected(true);
        } else {
            exerciseList.get(position).setItem_isSelected(false);
        }

    }

}