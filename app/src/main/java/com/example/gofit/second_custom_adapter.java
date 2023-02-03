package com.example.gofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class second_custom_adapter extends RecyclerView.Adapter<second_custom_adapter.myViewHolder> {

   private ArrayList<String> challenges ;
   private ArrayList<String> descriptions;
   private Context contex;

   public second_custom_adapter(Context context, ArrayList<String> challenges, ArrayList<String> descriptions) {
       this.contex = context ;
       this.challenges = challenges ;
       this.descriptions = descriptions ;
   }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex) ;
        View inflater_ = inflater.inflate(R.layout.second_row_template, parent, false) ;
        return new myViewHolder(inflater_) ;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.custom_challenges.setHint(challenges.get(position));
        holder.custom_descriptions.setHint(descriptions.get(position));
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private EditText custom_challenges ;
        private EditText custom_descriptions;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            custom_challenges = (EditText) itemView.findViewById(R.id.challenge_name)  ;
            custom_descriptions = (EditText)itemView.findViewById(R.id.description_name)  ;
        }
    }

}
