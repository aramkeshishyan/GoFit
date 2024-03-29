package com.example.gofit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.ExerciseOrMealType;
import com.example.gofit.data.model.requests.Steps;
import com.example.gofit.data.model.requests.UserStats;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.Challenges.ChallengeRecViewAdapter;
import com.example.gofit.recyclerViews.ExerciseRecViewAdapter;
import com.example.gofit.recyclerViews.RecommendExerciseAdapter;
import com.example.gofit.recyclerViews.RecommendNutritionAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First_Fragment extends Fragment implements ExerciseRecViewAdapter.OnNoteListener, SensorEventListener {
    private TextView step_count;
    private TextView calories_burned;
    private TextView calories_recommended;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private SharedPreferences sharedPreferences;

    private int previousStepCount = 0;
    private int stepCount;
    private RecyclerView exercise, nutrition, challenges;
    private ArrayList<Exercise_Item> exercise_list = new ArrayList<>();
    private ArrayList<Nutrition_Item> nutrition_list = new ArrayList<>();
    private ArrayList<ChallengeRecordDto> challenge_list = new ArrayList<>();
    private ArrayList<Friend> friendsList = new ArrayList<>();
    private ChallengeRecViewAdapter challengeAdapter ;
    private RecommendNutritionAdapter nutritionAdapter;
    private RecommendExerciseAdapter exerciseAdapter ;

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

        calories_recommended = view.findViewById(R.id.reccomended_calories);
        calories_recommended.setText(Integer.toString(sp.getInt("recCalories",0)));

        //Greet user with Hello + their first name
        TextView hello_greeting = (TextView) view.findViewById(R.id.hello_greeting);
        String fullName = sp.getString("fullName","");
        String firstName = fullName.split(" ")[0];  //split first/last name and get first name
        hello_greeting.setText("Hello " + firstName);

        determineExerciseRecommendations();
        determineMealReccomendations();

        exerciseAdapter = new RecommendExerciseAdapter(getContext(), exercise_list, this::onNoteClick);
        exerciseAdapter.setExercises(exercise_list);

        exercise = view.findViewById(R.id.rec_exercise_recview);
        exercise.setAdapter(exerciseAdapter);
        exercise.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        nutritionAdapter = new RecommendNutritionAdapter(getContext(), nutrition_list, this::onNoteClick2);
        nutritionAdapter.setNutritionArrayList(nutrition_list);

        nutrition = view.findViewById(R.id.rec_nutrition_recview);
        nutrition.setAdapter(nutritionAdapter);
        nutrition.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        challengeAdapter = new ChallengeRecViewAdapter(getContext(), challenge_list, this::onNoteClick3);
        challengeAdapter.setChallenges(challenge_list);

        challenges = view.findViewById(R.id.current_challenges_recview);
        challenges.setAdapter(challengeAdapter);
        challenges.setLayoutManager(new LinearLayoutManager(getContext()));
        challenge_list = new ArrayList<>() ;
        nutrition_list = new ArrayList<>();
        exercise_list = new ArrayList<>();

        userStatsCall();
        userFriendsCall();
        challengesCall();
        mealsByTypeCall(mealType);
        exercisesByTypeCall(exerciseType);


        //wait for 1 second for the async call to get the exercises from backend
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        calories_burned = view.findViewById(R.id.calories_burned);
        step_count = view.findViewById(R.id.step_taken);
        // Get the sensor manager and step counter sensor
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepCounterSensor == null) {
            Log.d("StepCounterFragment", "Device does not have step counter sensor");
            step_count.setText("0");
        }
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

    }


    private void determineExerciseRecommendations()
    {
        //Decide what Exercises to recommend
        if(Objects.equals(userGoal, "Gain Weight")) {
            exerciseType = "Strength";
        }
        else{
            exerciseType = "Cardio";
        }
    }

    private void determineMealReccomendations(){
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

    }

    private void userFriendsCall() {
        String token = sp.getString("token", "");
        MainApplication.apiManager.getFriends(token, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();

                if (response.isSuccessful() && responseFriends != null) {
                    ArrayList<Friend> tempList = new ArrayList<>();
                    tempList.addAll(responseFriends.getData());

                    friendsList = new ArrayList<>(tempList);

                }
                else {
                    Toast.makeText(getContext(),
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponseList<Friend>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());

            }
        });
    }

    private void challengesCall() {
        String token = sp.getString("token", "");

        MainApplication.apiManager.getChallengeRecords(token, new Callback<defaultResponseList<ChallengeRecordDto>>() {
            @Override
            public void onResponse(Call<defaultResponseList<ChallengeRecordDto>> call, Response<defaultResponseList<ChallengeRecordDto>> response) {
                defaultResponseList<ChallengeRecordDto> chalRecordsResponse = response.body();


                if (response.isSuccessful() && chalRecordsResponse != null) {
                    if (chalRecordsResponse.getData() != null) {
                        challenge_list.addAll(chalRecordsResponse.getData());
                        challengeAdapter.setChallenges(challenge_list);
                        challenges.setAdapter(challengeAdapter);

                    }
                    else {
//                        Toast.makeText(getContext(),
//                                "No challenges yet.",
//                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<defaultResponseList<ChallengeRecordDto>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("ChallengesCallTag", t.getMessage());
            }
        });

    }

    private void mealsByTypeCall(String recMealType) {
        ExerciseOrMealType mealType = new ExerciseOrMealType(recMealType);
        MainApplication.apiManager.getMealsByType(mealType, new Callback<defaultResponseList<Nutrition_Item>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Nutrition_Item>> call, Response<defaultResponseList<Nutrition_Item>> response) {
                defaultResponseList<Nutrition_Item> responseMeals = response.body();

                if(response.isSuccessful() && responseMeals != null){
                    nutrition_list.addAll(responseMeals.getData());
                    nutritionAdapter.setNutritionArrayList(nutrition_list);
                    nutrition.setAdapter(nutritionAdapter);

//                    Toast.makeText(getContext(),
//                            "Get Meals was Successful",
//                            Toast.LENGTH_SHORT).show();
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
                    exerciseAdapter.setExercises(exercise_list);
                    exercise.setAdapter(exerciseAdapter);

//                    Toast.makeText(getContext(),
//                            "Get Exercises was Successful",
//                            Toast.LENGTH_SHORT).show();

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

    public void onNoteClick3(int position){
        Intent intent = new Intent(getContext(), ChallengeActivity.class);
        intent.putExtra("chal_id", challenge_list.get(position).getChallengeId());
        intent.putExtra("chal_title", challenge_list.get(position).getTitle());
        intent.putExtra("chal_description", challenge_list.get(position).getDesc());

        Bundle args = new Bundle();
        ArrayList<Exercise_Item> exercise_list = challenge_list.get(position).getExerciseList();
        args.putSerializable("chal_exerciselist", exercise_list);
        intent.putExtras(args);
        //args.putSerializable("ARRAYLIST", (Serializable) challenge_list);
        //intent.putExtra("BUNDLE", args);

        //SEND FRIENDS LIST TO A CHALLENGE ITEM PAGE
        Bundle friendArgs = new Bundle();
        friendArgs.putSerializable("friends_list", friendsList);
        intent.putExtras(friendArgs);

        intent.putExtra("chal_durationDays", challenge_list.get(position).getDurationDays());
        intent.putExtra("chal_repetitions", challenge_list.get(position).getReps());
        intent.putExtra("chal_sets", challenge_list.get(position).getSets());
        intent.putExtra("chal_creator", challenge_list.get(position).getCreatorEmail());
        intent.putExtra("chal_date_accepted", challenge_list.get(position).getDateAccepted());
        intent.putExtra("chal_date_started", challenge_list.get(position).getDateStarted());
        intent.putExtra("chal_date_end", challenge_list.get(position).getDateEnd());
        intent.putExtra("chal_date_last_completed", challenge_list.get(position).getDateLastCompleted());
        intent.putExtra("chal_complete", challenge_list.get(position).isComplete());
        intent.putExtra("chal_streak", challenge_list.get(position).getStreak());
        intent.putExtra("chal_total_days_completed", challenge_list.get(position).getTotalDaysCompleted());
        intent.putExtra("chal_score", challenge_list.get(position).getScore());


        startActivity(intent);
    }


    public void calculateCalsBurned() {
        double calsBurned = stepCount * 0.04;   //average cals burned per step = 0.04
        String formattedCalsBurned = String.format("%.2f", calsBurned);
        calories_burned.setText(formattedCalsBurned);
        //calories_burned.setText(Double.toString(calsBurned));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) sensorEvent.values[0];
            step_count.setText(Integer.toString(stepCount));
            calculateCalsBurned();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Does nothing
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stepCounterSensor != null) {
            //Asks for Permission if it does not have it
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        1);
            } else {
                //Registers Listener
                sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
                Log.d("StepCounterFragment", "Step counter sensor registered");
                // Get the initial step count
                int addStepsToDatabase = stepCount - previousStepCount;
                updateStepsCall(addStepsToDatabase);
                stepCount = 0;
                previousStepCount = sharedPreferences.getInt("stepCount", 0);
                stepCount += previousStepCount;
                step_count.setText("Step Count: " + stepCount);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Sets Listener after getting permission
                sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
                Log.d("StepCounterFragment", "Step counter sensor registered");
                previousStepCount = sharedPreferences.getInt("stepCount", 0);
                stepCount += previousStepCount;
                step_count.setText(Integer.toString(stepCount));
            } else {
                //You did not give permission
                Log.d("StepCounterFragment", "Activity recognition permission denied");
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this);
            Log.d("StepCounterFragment", "Step counter sensor unregistered");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("stepCount", stepCount);
            editor.apply();
        }
    }

    private void updateStepsCall(int steps){
        String token = sp.getString("token", "");
        Steps stepsUp = new Steps(steps);

        MainApplication.apiManager.updateSteps(token, stepsUp, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {

                Toast.makeText(getContext(),
                        String.format("Response is %s", response.body().getMessage())
                        , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        });
    }


    private void userStatsCall(){
        String token = sp.getString("token", "");
        MainApplication.apiManager.getUserStats(token, new Callback<defaultResponse<UserStats>>() {
            @Override
            public void onResponse(Call<defaultResponse<UserStats>> call, Response<defaultResponse<UserStats>> response) {
                defaultResponse<UserStats> responseUserStats = response.body();


            }

            @Override
            public void onFailure(Call<defaultResponse<UserStats>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: ", Toast.LENGTH_LONG).show();
                Log.d("userStatsCallTag", t.getMessage());
            }
        });

    }
}