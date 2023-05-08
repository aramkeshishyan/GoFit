package com.example.gofit;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChallengeSelectAdapter extends RecyclerView.Adapter<ChallengeSelectAdapter.ViewHolder> {
    private ArrayList<Exercise_Item> exercise_list;
    private Context exercise_context;
    private OnNoteListener mOnNoteListener;

    public ChallengeSelectAdapter(ArrayList<Exercise_Item> exercise_list, Context exercise_context, OnNoteListener onNoteListener) {
        this.exercise_list = exercise_list;
        this.exercise_context = exercise_context;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(exercise_context).inflate(R.layout.challenge_select_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise_Item item = exercise_list.get(position);
        holder.title.setText(exercise_list.get(position).getItem_name());
        holder.group.setText(exercise_list.get(position).getItem_mGroup());
        holder.type.setText(exercise_list.get(position).getItem_type());
        holder.isChecked.setChecked(exercise_list.get(position).getItem_isSelected());
        Glide.with(exercise_context).asBitmap().load(exercise_list.get(position).getItem_image()).centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return exercise_list.size();
    }

    public void setExercise_list(ArrayList<Exercise_Item> exercise_list) {
        this.exercise_list = exercise_list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView group;
        private TextView type;
        private ImageView image;
        private CheckBox isChecked;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.ex_list_name);
            group = itemView.findViewById(R.id.ex_list_group);
            type = itemView.findViewById(R.id.ex_list_type);
            image = itemView.findViewById(R.id.ex_list_image);
            isChecked = itemView.findViewById(R.id.ex_list_checkbox);
            isChecked.setOnClickListener(this);

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
