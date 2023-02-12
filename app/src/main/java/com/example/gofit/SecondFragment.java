package com.example.gofit;

import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private RecyclerView exercise;
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

        ArrayList<RecView_Item> exerciseList = new ArrayList<>();
        exerciseList.add(new RecView_Item("Crunches", "Abdominal", "https://betterme.world/articles/wp-content/uploads/2020/10/How-Many-Calories-Do-You-Burn-Doing-Crunches.jpg"));
        exerciseList.add(new RecView_Item("Squats", "Quadriceps, Gluteus, Hips, Abdominals", "https://experiencelife.lifetime.life/wp-content/uploads/2021/02/Squat-1-1280x720.jpg"));
        exerciseList.add(new RecView_Item("Lunges", "Quadriceps, Hamstrings, Gluteus, Hips", "https://post.healthline.com/wp-content/uploads/2020/09/11159-Mix_things_up_with_this_lunge_and_bicep_curl_compound_move_732x549-thumbnail-732x549.jpg"));
        exerciseList.add(new RecView_Item("Push-up", "Pectorals, Triceps", "https://blog.nasm.org/hubfs/power-pushups.jpg"));
        exerciseList.add(new RecView_Item("Leg Raise", "Hips", "https://cathe.com/wp-content/uploads/2019/10/shutterstock_363953936.jpg"));
        exerciseList.add(new RecView_Item("Forearm Plank", "Abdominals", "https://www.wellandgood.com/wp-content/uploads/2019/03/GettyImages-855913544.jpg"));
        ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(getContext(),exerciseList);
        exerciseAdapter.setExercises(exerciseList);

        exercise = view.findViewById(R.id.exerciseRecView);
        exercise.setAdapter(exerciseAdapter);
        exercise.setLayoutManager(new LinearLayoutManager(getContext()));
        exercise.setHasFixedSize(true);

        exerciseAdapter.notifyDataSetChanged();

    }
}
