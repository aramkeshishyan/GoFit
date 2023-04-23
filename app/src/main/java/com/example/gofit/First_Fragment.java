package com.example.gofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gofit.data.model.requests.ExerciseOrMealType;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.ExerciseRecViewAdapter;
import com.example.gofit.recyclerViews.RecommendExerciseAdapter;
import com.example.gofit.recyclerViews.RecommendNutritionAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First_Fragment extends Fragment implements ExerciseRecViewAdapter.OnNoteListener{

    private RecyclerView exercise, nutrition;
    private ArrayList<Exercise_Item> exercise_list = new ArrayList<>();
    private ArrayList<Nutrition_Item> nutrition_list = new ArrayList<>();

    private SharedPreferences sp;
    private String userGoal;
    private String userBodyType;
    private String exerciseType;
    private String mealType;
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

        sp = getContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userGoal = sp.getString("goal","");
        userBodyType = sp.getString("bodyType", "");

        //Decide what Exercises to recommend
        if(Objects.equals(userGoal, "Gain Weight")) {
            exerciseType = "Strength";
        }
        else{
            exerciseType = "Cardio";
        }

        //Decide what Meals to recommend
        if(Objects.equals(userBodyType, "Ectomorph"))
        {
            if(Objects.equals(userGoal, "Gain Weight"))
            {
                mealType = "High Carbs";
            }
            else
            {
                mealType = "Light Protein";
            }

        }
        else if(Objects.equals(userBodyType, "Endomorph"))
        {
            if(Objects.equals(userGoal, "Gain Weight"))
            {
                mealType = "High Protein";
            }
            else
            {
                mealType = "Light Protein";
            }

        }
        else if(Objects.equals(userBodyType, "Mesomorph"))
        {
            if(Objects.equals(userGoal, "Gain Weight"))
            {
                mealType = "High Protein";
            }
            else
            {
                mealType = "Light Protein";
            }

        }

        //exercisesCall();
        mealsByTypeCall(mealType);
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

        RecommendNutritionAdapter nutritionAdapter = new RecommendNutritionAdapter(getContext(), nutrition_list, this::onNoteClick2);
        nutritionAdapter.setNutritionArrayList(nutrition_list);

        nutrition = view.findViewById(R.id.rec_nutrition_recview);
        nutrition.setAdapter(nutritionAdapter);
        nutrition.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void mealsByTypeCall(String recMealType) {
        ExerciseOrMealType mealType = new ExerciseOrMealType(recMealType);
        MainApplication.apiManager.getMealsByType(mealType, new Callback<defaultResponseList<Nutrition_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Nutrition_Item>> call, Response<defaultResponseList<Nutrition_Item>> response) {
                defaultResponseList<Nutrition_Item> responseMeals = response.body();

                if(response.isSuccessful() && responseMeals != null){
                    nutrition_list = new ArrayList<>();
                    nutrition_list.addAll(responseMeals.getData());

                    Toast.makeText(getContext(),
                            "Get Meals was Successful",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponseList<Nutrition_Item>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }


    private void exercisesByTypeCall(String userGoal) {

        ExerciseOrMealType exerciseType = new ExerciseOrMealType(userGoal);
        MainApplication.apiManager.getExercisesByType(exerciseType, new Callback<defaultResponseList<Exercise_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Exercise_Item>> call, Response<defaultResponseList<Exercise_Item>> response) {
                defaultResponseList<Exercise_Item> responseExercises = response.body();

                if (response.isSuccessful() && responseExercises != null) {
                    exercise_list = new ArrayList<>();
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


    public void onNoteClick2(int position){
        Intent intent = new Intent(getContext(), NutritionActivity.class);
        intent.putExtra("nt_id", nutrition_list.get(position).getItem_id());
        intent.putExtra("nt_title", nutrition_list.get(position).getItem_name());
        intent.putExtra("nt_type", nutrition_list.get(position).getItem_type());
        intent.putExtra("nt_calories", nutrition_list.get(position).getItem_calories());
        intent.putExtra("nt_proteins", nutrition_list.get(position).getItem_proteins());
        intent.putExtra("nt_carbs", nutrition_list.get(position).getItem_carbs());
        intent.putExtra("nt_fats", nutrition_list.get(position).getItem_fats());
        intent.putExtra("nt_image", nutrition_list.get(position).getItem_image());
        intent.putExtra("nt_ingredients", nutrition_list.get(position).getItem_ingredients());
        startActivity(intent);
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