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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendExerciseAdapter extends RecyclerView.Adapter<RecommendExerciseAdapter.ViewHolder> {
    private ArrayList<Exercise_Item> exerciseArrayList;
    private OnNoteListener onNoteListener;
    private Context exercise_context;

    public RecommendExerciseAdapter(Context exercise_context, ArrayList<Exercise_Item> exerciseArrayList, OnNoteListener onNoteListener) {

        this.exercise_context = exercise_context;
        this.exerciseArrayList = exerciseArrayList;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(exercise_context).inflate(R.layout.item_recommended_tile, parent, false);
        return new ViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise_Item exercise = exerciseArrayList.get(position);
        holder.item_name.setText(exercise.getItem_name());
        //Picasso.get().load(exercise.getItem_image()).into(holder.item_image);
        Glide.with(exercise_context).asBitmap().load(exercise.getItem_image()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }
    public void setExercises(ArrayList<Exercise_Item> exercises){
        this.exerciseArrayList = exercises;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView item_name;
        private ImageView item_image;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_rec_name);
            item_image = itemView.findViewById(R.id.item_rec_image);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) { onNoteListener.onNoteClick(getAdapterPosition());}
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
