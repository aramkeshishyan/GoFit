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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {

    private RecyclerView nutrition_RecView;
    private Spinner nutrition_spinner;
    private SearchView nutrition_searchview;
    private ArrayList<RecView_Item> nutritionList;
    private String[] nutrition_categories = {"All", "Type1", "Type2"};
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

        nutritionList = new ArrayList<>();
        nutritionList.add(new RecView_Item("Food1", "Type1", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food2", "Type2", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food3", "Type3", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food4", "Type4", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food5", "Type5", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));
        nutritionList.add(new RecView_Item("Food6", "Type6", "https://uxwing.com/wp-content/themes/uxwing/download/food-and-drinks/meal-food-icon.png"));

        NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(),nutritionList);
        nutritionAdapter.setNutritionArrayList(nutritionList);

        nutrition_RecView = view.findViewById(R.id.nutritionRecView);
        nutrition_RecView.setAdapter(nutritionAdapter);
        nutrition_RecView.setLayoutManager(new LinearLayoutManager(getContext()));
        nutrition_RecView.setHasFixedSize(true);

        nutrition_spinner = view.findViewById(R.id.nutrition_spinner);
        nutrition_spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nutrition_categories));

        //Spinner selection events
        nutrition_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (nutrition_categories[i] == "All"){
                    //set all meals into RecyclerView using NutritionList
                    NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(), nutritionList);
                    nutritionAdapter.setNutritionArrayList(nutritionList);
                    nutrition_RecView.setAdapter(nutritionAdapter);
                }
                else {
                    //Create new ArrayList
                    ArrayList<RecView_Item> newNutritionList = new ArrayList<>();
                    //for every item in nutritionList
                    for(RecView_Item checkNutritionList : nutritionList){
                        //compare if strings match with category selected
                        if(checkNutritionList.getItem_description() == nutrition_categories[i]){
                            //add item into new array list if category match
                            newNutritionList.add(checkNutritionList);
                        }
                    }
                    //set new list into RecyclerView
                    NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(), newNutritionList);
                    nutritionAdapter.setNutritionArrayList(newNutritionList);
                    nutrition_RecView.setAdapter(nutritionAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Search filtering
        nutrition_searchview = view.findViewById(R.id.nutrition_search);
        nutrition_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        //create new ArrayList
        ArrayList<RecView_Item> newNutritionList = new ArrayList<>();
        for(RecView_Item nutrition : nutritionList){
            //checks if entered string matches with the name of the item
            if(nutrition.getItem_name().toLowerCase().contains(s.toLowerCase())){
                newNutritionList.add((nutrition));
            }
        }
        if(newNutritionList.isEmpty()){
            Toast.makeText(getContext(), "No Items found.", Toast.LENGTH_SHORT).show();
        }
        else {
            //set new list into RecyclerView
            NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(), newNutritionList);
            nutritionAdapter.setNutritionArrayList(newNutritionList);
            nutrition_RecView.setAdapter(nutritionAdapter);
        }
    }
}