package com.example.gofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gofit.data.model.requests.ExerciseType;
import com.example.gofit.data.model.responses.defaultResponseList;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First_Fragment extends Fragment implements ExerciseRecViewAdapter.OnNoteListener{

    private RecyclerView exercise, nutrition;
    private ArrayList<Exercise_Item> exercise_list = new ArrayList<>();;
    private ArrayList<Nutrition_Item> nutrition_list = new ArrayList<>();

    private SharedPreferences sp;
    private String userGoal;
    private String exerciseType;
    public First_Fragment(){
        // require a empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //nutrition_list = new ArrayList<>();

        sp = getContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userGoal = sp.getString("goal","");

        if(Objects.equals(userGoal, "Gain Weight")) {
            exerciseType = "Strength";
        }
        else{
            exerciseType = "Cardio";
        }

//        exercise_list.add(new Exercise_Item("E1_C", "Crunches", "Core", "Hard", "Test",  "https://betterme.world/articles/wp-content/uploads/2020/10/How-Many-Calories-Do-You-Burn-Doing-Crunches.jpg", "Type"));
//        exercise_list.add(new Exercise_Item("E11_L", "Squats", "Legs", "Medium","Test", "https://experiencelife.lifetime.life/wp-content/uploads/2021/02/Squat-1-1280x720.jpg","Type"));
//        exercise_list.add(new Exercise_Item("E3-L", "Lunges", "Legs", "Easy","Test", "https://post.healthline.com/wp-content/uploads/2020/09/11159-Mix_things_up_with_this_lunge_and_bicep_curl_compound_move_732x549-thumbnail-732x549.jpg", "Type"));
//        exercise_list.add(new Exercise_Item("E8-C", "Push-up", "Chest", "Hard","Test", "https://blog.nasm.org/hubfs/power-pushups.jpg","Type"));
//        exercise_list.add(new Exercise_Item("E3_C", "Leg Raise", "Core", "Hard","Test", "https://cathe.com/wp-content/uploads/2019/10/shutterstock_363953936.jpg","Type"));
//        exercise_list.add(new Exercise_Item("E5-A", "Plank", "Abs", "Hard","Test", "https://www.wellandgood.com/wp-content/uploads/2019/03/GettyImages-855913544.jpg","Type"));
        //exercisesCall();
        exercisesByTypeCall(exerciseType);

        //wait for 1 second for the async call to get the exercises from backend
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        RecommendExerciseAdapter exerciseAdapter = new RecommendExerciseAdapter(getContext(), exercise_list, this::onNoteClick);
        exerciseAdapter.setExercises(exercise_list);

        exercise = view.findViewById(R.id.rec_exercise_recview);
        exercise.setAdapter(exerciseAdapter);
        exercise.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void exercisesByTypeCall(String userGoal) {

        ExerciseType exerciseType = new ExerciseType(userGoal);
        MainApplication.apiManager.getExercisesByType(exerciseType, new Callback<defaultResponseList<Exercise_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Exercise_Item>> call, Response<defaultResponseList<Exercise_Item>> response) {
                defaultResponseList<Exercise_Item> responseExercises = response.body();

                if (response.isSuccessful() && responseExercises != null) {
                    exercise_list.addAll(responseExercises.getData());

                    Toast.makeText(getContext(),
                            "Get Exercises was Successful",
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getContext(),
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponseList<Exercise_Item>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(), ExerciseActivity.class);
        intent.putExtra("ex_id", exercise_list.get(position).getItem_id());
        intent.putExtra("ex_title", exercise_list.get(position).getItem_name());
        intent.putExtra("ex_group", exercise_list.get(position).getItem_mGroup());
        intent.putExtra("ex_level", exercise_list.get(position).getItem_level());
        intent.putExtra("ex_description", exercise_list.get(position).getItem_description());
        intent.putExtra("ex_imageUrl", exercise_list.get(position).getItem_image());
        intent.putExtra("ex_type", exercise_list.get(position).getItem_type());
        startActivity(intent);
        Toast.makeText(getContext(), exercise_list.get(position).getItem_name() + " has been pressed.", Toast.LENGTH_SHORT).show();
    }
}