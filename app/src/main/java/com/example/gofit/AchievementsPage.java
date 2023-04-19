package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.gofit.recyclerViews.AchievementsRV_Adapter;

import java.util.ArrayList;

public class AchievementsPage extends AppCompatActivity {
    private RecyclerView achievement;
    private String[] achievement_categories = {"All", "Complete", "Incomplete"};
    private ImageView back_button;
    private ArrayList<Achievements> achievement_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_page);

        back_button = findViewById(R.id.ach_backbutton);
        back_button.setOnClickListener(view -> AchievementsPage.super.finish());

        achievement = findViewById(R.id.achievementRecView);
        achievement_list = new ArrayList<>();
        achievement_list.add(new Achievements(1, 0, 0 ,"Achievement 1", "description1"));
        achievement_list.add(new Achievements(2, 0, 0 ,"Achievement 2", "description2"));
        achievement_list.add(new Achievements(3, 0, 0 ,"Achievement 3", "description3"));

        AchievementsRV_Adapter ach_adapter = new AchievementsRV_Adapter();
        ach_adapter.setAchievementList(achievement_list);

        achievement.setAdapter(ach_adapter);
        achievement.setLayoutManager(new LinearLayoutManager(this));
        achievement.setHasFixedSize(true);
    }
}