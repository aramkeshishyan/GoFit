package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gofit.Exercise_Item;
import com.example.gofit.R;

import java.util.ArrayList;

public class ChallengeCreateAdapter extends RecyclerView.Adapter<ChallengeCreateAdapter.ViewHolder> {
    private ArrayList<Exercise_Item> exerciseList = new ArrayList<>();
    private Context exercise_context;
    public ChallengeCreateAdapter(Context exercise_context, ArrayList<Exercise_Item> exercises) {
        this.exercise_context = exercise_context;
        this.exerciseList = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(exercise_context).inflate(R.layout.item_recommended_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Exercise_Item exercise = exerciseList.get(position);
        holder.title.setText(exerciseList.get(position).getItem_name());
        Glide.with(exercise_context).asBitmap().load(exerciseList.get(position).getItem_image()).centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public void setExercise(ArrayList<Exercise_Item> exercise) {
        this.exerciseList = exercise;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_rec_name);
            image = itemView.findViewById(R.id.item_rec_image);
        }
    }
}
