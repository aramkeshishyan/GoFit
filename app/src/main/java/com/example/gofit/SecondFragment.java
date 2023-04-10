package com.example.gofit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gofit.data.model.responses.defaultResponseList;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment implements ExerciseRecViewAdapter.OnNoteListener {
    private RecyclerView exercisesRecyclerView;
    private ExerciseRecViewAdapter exerciseAdapter;
    private ArrayList<Exercise_Item> exerciseList = new ArrayList<>();
    private ArrayList<Exercise_Item> exerciseList2;
    private View myView;
    private Spinner exercise_spinner;
    private String[] exercise_categories = {"All", "Core", "Legs", "Arms", "Chest", "Shoulder", "Biceps", "Triceps", "Back", "Legs", "Abs"};
    private SearchView exercise_searchview;
    public SecondFragment(){
        // require a empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exerciseList.add(new Exercise_Item("E1_C", "Crunches", "Core", "Hard", "Test",  "https://betterme.world/articles/wp-content/uploads/2020/10/How-Many-Calories-Do-You-Burn-Doing-Crunches.jpg", "Cardio"));
        exerciseList.add(new Exercise_Item("E11_L", "Squats", "Legs", "Medium","Test", "https://experiencelife.lifetime.life/wp-content/uploads/2021/02/Squat-1-1280x720.jpg", "Cardio"));
        exerciseList.add(new Exercise_Item("E3-L", "Lunges", "Legs", "Easy","Test", "https://post.healthline.com/wp-content/uploads/2020/09/11159-Mix_things_up_with_this_lunge_and_bicep_curl_compound_move_732x549-thumbnail-732x549.jpg","Cardio"));
        exerciseList.add(new Exercise_Item("E8-C", "Push-up", "Chest", "Hard","Test", "https://blog.nasm.org/hubfs/power-pushups.jpg","Strength"));
        exerciseList.add(new Exercise_Item("E3_C", "Leg Raise", "Core", "Hard","Test", "https://cathe.com/wp-content/uploads/2019/10/shutterstock_363953936.jpg","Cardio"));
        exerciseList.add(new Exercise_Item("E5-A", "Plank", "Abs", "Hard","Test", "https://www.wellandgood.com/wp-content/uploads/2019/03/GettyImages-855913544.jpg","Strength"));

        //exercisesCall();

        //wait for 1 second for the async call to get the exercises from backend
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        exerciseList2 = exerciseList;
        exerciseAdapter = new ExerciseRecViewAdapter(getContext(),exerciseList, this);
        exerciseAdapter.setExercises(exerciseList);

        exercisesRecyclerView = view.findViewById(R.id.exerciseRecView);
        exercisesRecyclerView.setAdapter(exerciseAdapter);
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exercisesRecyclerView.setHasFixedSize(true);

        exerciseAdapter.notifyDataSetChanged();

        exercise_spinner = view.findViewById(R.id.exercise_spinner);
        exercise_spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exercise_categories));

        //Spinner selection events
        exercise_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(exercise_categories[i] == "All"){
                    //set all exercises in RecView using exerciseList
                    ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(),exerciseList, SecondFragment.this);
                    exerciseAdapter.setExercises(exerciseList);
                    exercisesRecyclerView.setAdapter(exerciseAdapter);
                    exerciseList2 = exerciseList;

                }
                else {
                    //create new Arraylist
                    ArrayList<Exercise_Item> newExerciseList = new ArrayList<>();
                    //for every item in exerciseList
                    for (Exercise_Item checkExerciseList : exerciseList) {
                        //comparing if strings match with category selected
                        if (checkExerciseList.getItem_mGroup() == exercise_categories[i]) {
                            //add item into new array list if category match
                            newExerciseList.add(checkExerciseList);
                        }
                    }
                    //set new list into RecView
                    exerciseList2 = newExerciseList;
                    ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(), newExerciseList, SecondFragment.this);
                    exerciseAdapter.setExercises(newExerciseList);
                    exercisesRecyclerView.setAdapter(exerciseAdapter);

                    //Toast.makeText(getContext().getApplicationContext(), exercise_categories[i], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Search Filtering
        exercise_searchview = view.findViewById(R.id.exercise_search);
        exercise_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
    }

    private void exercisesCall(){
        MainApplication.apiManager.getExercises(new Callback<defaultResponseList<Exercise_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Exercise_Item>> call, Response<defaultResponseList<Exercise_Item>> response) {
                defaultResponseList<Exercise_Item> responseExercises = response.body();

                if (response.isSuccessful() && responseExercises != null) {
                    exerciseList.addAll(responseExercises.getData());

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

    private void filter(String s) {
        //create new Arraylist
        ArrayList<Exercise_Item> newExerciseList = new ArrayList<>();
        for (Exercise_Item exercise : exerciseList){
            //checks if entered string matches with the name of the item
            if (exercise.getItem_name().toLowerCase().contains(s.toLowerCase())){
                newExerciseList.add(exercise);
            }
        }
        if (newExerciseList.isEmpty()){
            Toast.makeText(getContext(), "No Exercise found.", Toast.LENGTH_SHORT).show();
        }
        else {
            //set new list into RecView
            ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(), newExerciseList, this);
            exerciseAdapter.setExercises(newExerciseList);
            exercisesRecyclerView.setAdapter(exerciseAdapter);
            exerciseList2 = newExerciseList;

        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(), ExerciseActivity.class);
        intent.putExtra("ex_id", exerciseList2.get(position).getItem_id());
        intent.putExtra("ex_title", exerciseList2.get(position).getItem_name());
        intent.putExtra("ex_group", exerciseList2.get(position).getItem_mGroup());
        intent.putExtra("ex_level", exerciseList2.get(position).getItem_level());
        intent.putExtra("ex_description", exerciseList2.get(position).getItem_description());
        intent.putExtra("ex_imageUrl", exerciseList2.get(position).getItem_image());
        intent.putExtra("ex_type", exerciseList2.get(position).getItem_type());
        startActivity(intent);
        Toast.makeText(getContext(), exerciseList2.get(position).getItem_name() + " has been pressed.", Toast.LENGTH_SHORT).show();
    }
}
