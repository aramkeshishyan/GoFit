package com.example.gofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementsRV_Adapter extends RecyclerView.Adapter<AchievementsRV_Adapter.ViewHolder> {
    private ArrayList<Achievements> AchievementList;

    public AchievementsRV_Adapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Achievements ach_list = AchievementList.get(position);
        holder.item_title.setText(ach_list.getTitle());
        holder.item_description.setText(ach_list.getDescription());
    }

    @Override
    public int getItemCount() {
        return AchievementList.size();
    }

    public void setAchievementList(ArrayList<Achievements> achievementList) {
        AchievementList = achievementList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView item_title;
    private TextView item_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.ach_title);
            item_description = itemView.findViewById(R.id.ach_description);
        }
    }
}
