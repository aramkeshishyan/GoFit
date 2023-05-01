package com.example.gofit.recyclerViews.Challenges;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Challenge_ExerciseRV_Adapter extends RecyclerView.Adapter<Challenge_ExerciseRV_Adapter.ViewHolder>{
    private ArrayList<Exercise_Item> exercise_List;
    private Context exercise_context;
    private OnNoteListener mOnNoteListener;

    public Challenge_ExerciseRV_Adapter(Context exercise_context, ArrayList<Exercise_Item> exercise_list, OnNoteListener onNoteListener) {
        this.exercise_context = exercise_context;
        this.exercise_List = exercise_list;
        this.mOnNoteListener = onNoteListener;
    }

    public void setExercise_List(ArrayList<Exercise_Item> exercises){
        this.exercise_List = exercises;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(exercise_context).inflate(R.layout.challenge_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise_Item exercise = exercise_List.get(position);
        holder.title.setText(exercise.getItem_name());
        holder.muscle_group.setText(exercise.getItem_mGroup());
        holder.exercise_lvl.setText(exercise.getItem_level());
        holder.type.setText(exercise.getItem_type());
        Picasso.get().load(exercise.getItem_image()).into(holder.image_url);
        Glide.with(exercise_context).asBitmap().load(exercise.getItem_image()).centerCrop().into(holder.image_url);
    }

    @Override
    public int getItemCount() {
        return exercise_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView muscle_group;
        private TextView type;
        private TextView exercise_lvl;
        private ImageView image_url;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.Challenge_item_title);
            muscle_group = itemView.findViewById(R.id.Challenge_item_musclegroup);
            type = itemView.findViewById(R.id.Challenge_item_type);
            exercise_lvl = itemView.findViewById(R.id.Challenge_item_exerciselvl);
            image_url = itemView.findViewById(R.id.Challenge_item_image);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
