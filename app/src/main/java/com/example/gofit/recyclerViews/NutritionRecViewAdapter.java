package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofit.Nutrition_Item;
import com.example.gofit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NutritionRecViewAdapter extends RecyclerView.Adapter<NutritionRecViewAdapter.ViewHolder>{

    private ArrayList<Nutrition_Item> nutritionArrayList;
    private Context nutrition_context;
    private OnNoteListener mOnNoteListener;

    public NutritionRecViewAdapter(Context nutrition_context, ArrayList<Nutrition_Item> nutrition_list, OnNoteListener onNoteListener) {
        this.nutrition_context = nutrition_context;
        this.nutritionArrayList = nutrition_list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nutrition_context).inflate(R.layout.recycle_view_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nutrition_Item nutrition_list = nutritionArrayList.get(position);
        holder.item_name.setText(nutrition_list.getItem_name());
        holder.item_type.setText(nutrition_list.getItem_type());
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
        private TextView item_type;
        private ImageView item_image;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            item_name = itemView.findViewById(R.id.rv_name);
            item_type = itemView.findViewById(R.id.rv_description);
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