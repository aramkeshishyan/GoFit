package com.example.gofit;

import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private RecyclerView exercise;
    private Spinner exercise_spinner;
    private String[] exercise_categories = {"All", "Core", "Upper", "Lower"};
    private SearchView exercise_searchview;
    private ArrayList<RecView_Item> exerciseList;
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

        exerciseList = new ArrayList<>();
        exerciseList.add(new RecView_Item("Crunches", "Core", "https://betterme.world/articles/wp-content/uploads/2020/10/How-Many-Calories-Do-You-Burn-Doing-Crunches.jpg"));
        exerciseList.add(new RecView_Item("Squats", "Lower", "https://experiencelife.lifetime.life/wp-content/uploads/2021/02/Squat-1-1280x720.jpg"));
        exerciseList.add(new RecView_Item("Lunges", "Lower", "https://post.healthline.com/wp-content/uploads/2020/09/11159-Mix_things_up_with_this_lunge_and_bicep_curl_compound_move_732x549-thumbnail-732x549.jpg"));
        exerciseList.add(new RecView_Item("Push-up", "Upper", "https://blog.nasm.org/hubfs/power-pushups.jpg"));
        exerciseList.add(new RecView_Item("Leg Raise", "Lower", "https://cathe.com/wp-content/uploads/2019/10/shutterstock_363953936.jpg"));
        exerciseList.add(new RecView_Item("Forearm Plank", "Core", "https://www.wellandgood.com/wp-content/uploads/2019/03/GettyImages-855913544.jpg"));
        ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(),exerciseList);
        exerciseAdapter.setExercises(exerciseList);

        exercise = view.findViewById(R.id.exerciseRecView);
        exercise.setAdapter(exerciseAdapter);
        exercise.setLayoutManager(new LinearLayoutManager(getContext()));
        exercise.setHasFixedSize(true);

        exerciseAdapter.notifyDataSetChanged();

        exercise_spinner = view.findViewById(R.id.exercise_spinner);
        exercise_spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exercise_categories));

        //Spinner selection events
        exercise_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(exercise_categories[i] == "All"){
                    //set all exercises in RecView using exerciseList
                    ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(),exerciseList);
                    exerciseAdapter.setExercises(exerciseList);
                    exercise.setAdapter(exerciseAdapter);
                }
                else {
                    //create new Arraylist
                    ArrayList<RecView_Item> newExerciseList = new ArrayList<>();
                    //for every item in exerciseList
                    for (RecView_Item checkExerciseList : exerciseList) {
                        //comparing if strings match with category selected
                        if (checkExerciseList.getItem_description() == exercise_categories[i]) {
                            //add item into new array list if category match
                            newExerciseList.add(checkExerciseList);
                        }
                    }
                    //set new list into RecView
                    ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(), newExerciseList);
                    exerciseAdapter.setExercises(newExerciseList);
                    exercise.setAdapter(exerciseAdapter);

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

    private void filter(String s) {
        //create new Arraylist
        ArrayList<RecView_Item> newExerciseList = new ArrayList<>();
        for (RecView_Item exercise : exerciseList){
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
            ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(), newExerciseList);
            exerciseAdapter.setExercises(newExerciseList);
            exercise.setAdapter(exerciseAdapter);
        }
    }
}
