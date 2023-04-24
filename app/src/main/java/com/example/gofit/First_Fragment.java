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
import android.widget.TextView;
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

        //Greet user with Hello + their first name
        TextView hello_greeting = (TextView) view.findViewById(R.id.hello_greeting);
        String fullName = sp.getString("fullName","");
        String firstName = fullName.split(" ")[0];  //split first/last name and get first name
        hello_greeting.setText("Hello " + firstName);

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

        //addNutritionItems();
//        exercise_list.add(new Exercise_Item("E1_C", "Crunches", "Core", "Hard", "Test",  "https://betterme.world/articles/wp-content/uploads/2020/10/How-Many-Calories-Do-You-Burn-Doing-Crunches.jpg", "Type"));
//        exercise_list.add(new Exercise_Item("E11_L", "Squats", "Legs", "Medium","Test", "https://experiencelife.lifetime.life/wp-content/uploads/2021/02/Squat-1-1280x720.jpg","Type"));
//        exercise_list.add(new Exercise_Item("E3-L", "Lunges", "Legs", "Easy","Test", "https://post.healthline.com/wp-content/uploads/2020/09/11159-Mix_things_up_with_this_lunge_and_bicep_curl_compound_move_732x549-thumbnail-732x549.jpg", "Type"));
//        exercise_list.add(new Exercise_Item("E8-C", "Push-up", "Chest", "Hard","Test", "https://blog.nasm.org/hubfs/power-pushups.jpg","Type"));
//        exercise_list.add(new Exercise_Item("E3_C", "Leg Raise", "Core", "Hard","Test", "https://cathe.com/wp-content/uploads/2019/10/shutterstock_363953936.jpg","Type"));
//        exercise_list.add(new Exercise_Item("E5-A", "Plank", "Abs", "Hard","Test", "https://www.wellandgood.com/wp-content/uploads/2019/03/GettyImages-855913544.jpg","Type"));
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
    private void addNutritionItems() {
        nutrition_list.add(new Nutrition_Item("M1_B",
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
        nutrition_list.add(new Nutrition_Item("M2_B",
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
        nutrition_list.add(new Nutrition_Item("M3_B",
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
        nutrition_list.add(new Nutrition_Item("M4_L",
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
        nutrition_list.add(new Nutrition_Item("M5_L",
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
        nutrition_list.add(new Nutrition_Item("M6_S",
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