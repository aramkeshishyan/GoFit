package com.example.gofit;

import android.content.Intent;
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

import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.NutritionRecViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdFragment extends Fragment implements NutritionRecViewAdapter.OnNoteListener{

    private RecyclerView nutrition_RecView;
    private NutritionRecViewAdapter nutritionAdapter;
    private ArrayList<Nutrition_Item> nutritionList = new ArrayList<>();
    private ArrayList<Nutrition_Item> nutritionList2;
    private Spinner nutrition_spinner;
    private String[] nutrition_categories = {"All", "Breakfast", "Lunch", "Dinner", "Snack", "Dessert"};
    private SearchView nutrition_searchview;
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

        //addItemsIntoList();
        mealsCall();

        //wait for 1 second for the async call to get the exercises from backend
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        nutritionList2 = nutritionList;
        nutritionAdapter = new NutritionRecViewAdapter(getContext(),nutritionList, this);
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
                    NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(), nutritionList, ThirdFragment.this);
                    nutritionAdapter.setNutritionArrayList(nutritionList);
                    nutrition_RecView.setAdapter(nutritionAdapter);
                    nutritionList2 = nutritionList;
                }
                else {
                    //Create new ArrayList
                    ArrayList<Nutrition_Item> newNutritionList = new ArrayList<>();
                    //for every item in nutritionList
                    for(Nutrition_Item checkNutritionList : nutritionList){
                        //compare if strings match with category selected
                        if(checkNutritionList.getItem_type() == nutrition_categories[i]){
                            //add item into new array list if category match
                            newNutritionList.add(checkNutritionList);
                        }
                    }
                    //set new list into RecyclerView
                    nutritionList2 = newNutritionList;
                    NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(), newNutritionList, ThirdFragment.this);
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

    private void mealsCall() {
        MainApplication.apiManager.getMeals(new Callback<defaultResponseList<Nutrition_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Nutrition_Item>> call, Response<defaultResponseList<Nutrition_Item>> response) {
                defaultResponseList<Nutrition_Item> responseMeals = response.body();

                if(response.isSuccessful() && responseMeals != null){
                    nutritionList.addAll(responseMeals.getData());

                    Toast.makeText(getContext(),
                            "Get Meals was Successful",
                            Toast.LENGTH_SHORT).show();

                    Toast.makeText(getContext(),
                            String.format("%s", responseMeals.getData().get(0).getItem_fats()),
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


    private void filter(String s) {
        //create new ArrayList
        ArrayList<Nutrition_Item> newNutritionList = new ArrayList<>();
        for(Nutrition_Item nutrition : nutritionList){
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
            NutritionRecViewAdapter nutritionAdapter = new NutritionRecViewAdapter(getContext(), newNutritionList, this);
            nutritionAdapter.setNutritionArrayList(newNutritionList);
            nutrition_RecView.setAdapter(nutritionAdapter);
            nutritionList2 = newNutritionList;
        }
    }
    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(), NutritionActivity.class);
        intent.putExtra("nt_id", nutritionList2.get(position).getItem_id());
        intent.putExtra("nt_title", nutritionList2.get(position).getItem_name());
        intent.putExtra("nt_type", nutritionList2.get(position).getItem_type());
        intent.putExtra("nt_calories", nutritionList2.get(position).getItem_calories());
        intent.putExtra("nt_proteins", nutritionList2.get(position).getItem_proteins());
        intent.putExtra("nt_carbs", nutritionList2.get(position).getItem_carbs());
        intent.putExtra("nt_fats", nutritionList2.get(position).getItem_fats());
        intent.putExtra("nt_image", nutritionList2.get(position).getItem_image());
        intent.putExtra("nt_ingredients", nutritionList2.get(position).getItem_ingredients());
        startActivity(intent);
    }
    private void addItemsIntoList() {
        nutritionList.add(new Nutrition_Item("M1_B",
                "Caramel Brownie Smoothie Bowl",
                "Breakfast", "532cal",
                "11g", "58g", "26g",
                "https://upload.wikimedia.org/wikipedia/commons/1/1d/Chocolate_Green_Smoothie_Bowl.jpg",
                "• 1 stoned date\n" +
                        "• 1 frozen banana\n" +
                        "• 150ml plant milk (e.g. almond milk)\n" +
                        "• 3 tsp hazelnut butter\n" +
                        "• 2 tsp raw, low-fat cocoa powder\n" +
                        "• vanilla (ground)\n" +
                        "• 1 pinch salt\n" +
                        "• 1 tsp ceylon cinnamon powder\n" +
                        "• 20g rawnola\n" +
                        "• 1/2 banana\n" +
                        "• 1 tsp cocoa nibs"));
        nutritionList.add(new Nutrition_Item("M2_B",
                "Sweet Scrambled Eggs with Banana",
                "Breakfast",
                "524cal", "20g", "35g", "34g",
                "https://images.pexels.com/photos/13020203/pexels-photo-13020203.jpeg",
                "\"• 1 ripe banana\n" +
                        "• 2 medium eggs\n" +
                        "• 2 tbsp creamy coconut milk\n" +
                        "• 1 tsp coconut oil\n" +
                        "• 1 tsp grated coconut\n" +
                        "• 10g chopped almonds\n" +
                        "• 2 tsp almond butter\n" +
                        "• 1/2 pear\""));
        nutritionList.add(new Nutrition_Item("M3_B",
                "Cinnamon Roll Porridge",
                "Breakfast",
                "394 cal", "8g", "48g", "16g",
                "https://i0.hippopx.com/photos/123/293/154/muesli-breakfast-food-vegan-preview.jpg",
                "\"• 30g apple\n" +
                        "• 50g oats\n" +
                        "• 3/4 tsp ceylon cinnamon powder\n" +
                        "• 1/4 tsp grated nutmeg\n" +
                        "• 1/4 tsp vanilla (ground)\n" +
                        "• 1/2 tsp baking soda\n" +
                        "• 100ml plant milk\n" +
                        "• 25g apple purée\n" +
                        "• 1 tsp coconut oil\n" +
                        "• 10g chocolate chips\""));
        nutritionList.add(new Nutrition_Item("M4_L",
                "Minced Beef Stir-Fry",
                "Lunch",
                "500 cal", "29g", "56g", "19g",
                "https://live.staticflickr.com/65535/51362714116_c564512ca7_b.jpg",
                "\"• 50g uncooked rice (e.g. long grain or brown rice)\n" +
                        "• salt\n" +
                        "• 1-2 tsp coconut or avocado oil\n" +
                        "• pepper\n" +
                        "• chili powder\n" +
                        "• 100g low-fat minced beef\n" +
                        "• 100g zucchini\n" +
                        "• 100g sweet corn\n" +
                        "• 1 handful basil\""));
        nutritionList.add(new Nutrition_Item("M5_L",
                "Scrambled Eggs with Ham",
                "Lunch/Breakfast",
                "393 cal", "32g", "7g", "26g",
                "https://live.staticflickr.com/4147/5046938557_f7622b6880_b.jpg",
                "\"• 50g cherry tomatoes\n" +
                        "• 50g mushrooms\n" +
                        "• 1 small handful herbs\n" +
                        "• 50g turkey ham\n" +
                        "• 2 tsp olive oil\n" +
                        "• salt (by taste)\n" +
                        "• pepper (by taste)\n" +
                        "• 3 eggs\""));
        nutritionList.add(new Nutrition_Item("M6_S",
                "Savory Rice Cakes",
                "Snack",
                "290 cal", "6g", "25g", "18g",
                "https://upload.wikimedia.org/wikipedia/commons/9/9d/Rice_cakes_with_smoked_snoek_pate.jpg",
                "\"• 4 rice cakes\n" +
                        "• 40g avocado\n" +
                        "• 30g vegan cream cheese\n" +
                        "• sun-dried tomatoes\n" +
                        "• cucumber\n" +
                        "• olives\n" +
                        "• rocket\n" +
                        "• salt\n" +
                        "• pepper\n" +
                        "• chili\n" +
                        "• herbs\""));

    }


}