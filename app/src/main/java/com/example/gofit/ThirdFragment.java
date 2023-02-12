package com.example.gofit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {

    private RecyclerView nutrition_RecView;
    public ThirdFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<RecView_Item> nutritionList = new ArrayList<>();
        nutritionList.add(new RecView_Item("Food1", "Info1", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food2", "Info2", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food3", "Info3", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food4", "Info4", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food5", "Info5", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food6", "Info6", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));

        NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(),nutritionList);
        nutritionAdapter.setNutritionArrayList(nutritionList);

        nutrition_RecView = view.findViewById(R.id.nutritionRecView);
        nutrition_RecView.setAdapter(nutritionAdapter);
        nutrition_RecView.setLayoutManager(new LinearLayoutManager(getContext()));
        nutrition_RecView.setHasFixedSize(true);

    }
}