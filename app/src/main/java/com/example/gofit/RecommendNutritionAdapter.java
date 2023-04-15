package com.example.gofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendNutritionAdapter extends RecyclerView.Adapter<RecommendNutritionAdapter.ViewHolder>{

    private ArrayList<Nutrition_Item> nutritionArrayList;
    private Context nutrition_context;
    private OnNoteListener mOnNoteListener;

    public RecommendNutritionAdapter(Context nutrition_context, ArrayList<Nutrition_Item> nutrition_list, OnNoteListener onNoteListener) {
        this.nutrition_context = nutrition_context;
        this.nutritionArrayList = nutrition_list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nutrition_context).inflate(R.layout.item_recommended_tile, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nutrition_Item nutrition_list = nutritionArrayList.get(position);
        holder.item_name.setText(nutrition_list.getItem_name());
        Picasso.get().load(nutrition_list.getItem_image()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return nutritionArrayList.size();
    }

    public void setNutritionArrayList(ArrayList<Nutrition_Item> nutritionArrayList) {
        this.nutritionArrayList = nutritionArrayList;
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
        public void onClick(View view) {
            onNoteListener.onNoteClick2(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick2(int position);
    }
}