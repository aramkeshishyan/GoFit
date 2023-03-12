package com.example.gofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseRecViewAdapter extends RecyclerView.Adapter<ExerciseRecViewAdapter.ViewHolder> {
    private ArrayList<RecView_Item> exercisesArrayList;
    private Context exercise_context;
    public ExerciseRecViewAdapter(Context exercise_context, ArrayList<RecView_Item> exercises) {
        this.exercise_context = exercise_context;
        this.exercisesArrayList = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(exercise_context).inflate(R.layout.recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecView_Item exercise = exercisesArrayList.get(position);
        holder.item_name.setText(exercise.getItem_name());
        holder.item_description.setText(exercise.getItem_description());
        Picasso.get().load(exercise.getItem_image()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return exercisesArrayList.size();
    }

    public void setExercises(ArrayList<RecView_Item> exercises) {
        this.exercisesArrayList = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_name;
        private TextView item_description;
        private ImageView item_image;
        private LinearLayout item_parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.rv_name);
            item_description = itemView.findViewById(R.id.rv_description);
            item_image = itemView.findViewById(R.id.rv_image);
            item_parent = itemView.findViewById(R.id.item_parent);
        }
    }
}
