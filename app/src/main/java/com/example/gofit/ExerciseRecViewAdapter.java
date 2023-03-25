package com.example.gofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;





import java.util.ArrayList;

public class ExerciseRecViewAdapter extends RecyclerView.Adapter<ExerciseRecViewAdapter.ViewHolder> {
    private ArrayList<Exercise_Item> exercisesArrayList;
    private OnNoteListener mOnNoteListener;
    private Context exercise_context;
    public ExerciseRecViewAdapter(Context exercise_context, ArrayList<Exercise_Item> exercises, OnNoteListener onNoteListener) {
        this.exercise_context = exercise_context;
        this.exercisesArrayList = exercises;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(exercise_context).inflate(R.layout.recycle_view_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise_Item exercise = exercisesArrayList.get(position);
        holder.item_name.setText(exercise.getItem_name());
        holder.item_group.setText(exercise.getItem_mGroup());
        Picasso.get().load(exercise.getItem_image()).into(holder.item_image);

    }

    @Override
    public int getItemCount() {
        return exercisesArrayList.size();
    }

    public void setExercises(ArrayList<Exercise_Item> exercises) {
        this.exercisesArrayList = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView item_name;
        private TextView item_group;
        private ImageView item_image;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            item_name = itemView.findViewById(R.id.rv_name);
            item_group = itemView.findViewById(R.id.rv_description);
            item_image = itemView.findViewById(R.id.rv_image);
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
