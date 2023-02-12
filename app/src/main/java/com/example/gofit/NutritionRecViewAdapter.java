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

public class NutritionRecViewAdapter extends RecyclerView.Adapter<NutritionRecViewAdapter.ViewHolder>{

    private ArrayList<RecView_Item> nutritionArrayList;
    private Context nutrition_context;

    public NutritionRecViewAdapter(Context nutrition_context, ArrayList<RecView_Item> nutrition_list) {
        this.nutrition_context = nutrition_context;
        this.nutritionArrayList = nutrition_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nutrition_context).inflate(R.layout.recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecView_Item nutrition_list = nutritionArrayList.get(position);
        holder.item_name.setText(nutrition_list.getItem_name());
        holder.item_description.setText(nutrition_list.getItem_description());
        Picasso.get().load(nutrition_list.getItem_image()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return nutritionArrayList.size();
    }

    public void setNutritionArrayList(ArrayList<RecView_Item> nutritionArrayList) {
        this.nutritionArrayList = nutritionArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_name;
        private TextView item_description;
        private ImageView item_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.rv_name);
            item_description = itemView.findViewById(R.id.rv_description);
            item_image = itemView.findViewById(R.id.rv_image);
        }
    }
}
