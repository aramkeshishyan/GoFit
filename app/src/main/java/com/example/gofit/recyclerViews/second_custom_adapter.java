package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofit.R;
import com.example.gofit.removeItemListner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class second_custom_adapter extends RecyclerView.Adapter<second_custom_adapter.myViewHolder> {

   public ArrayList<String> challenges ;

   public ArrayList<String> descriptions;

   private FloatingActionButton cb ;

   private removeItemListner listener ;
   private Context contex;

   public second_custom_adapter(Context context, ArrayList<String> challenges, ArrayList<String> descriptions) {
       this.contex = context ;
       this.challenges = challenges ;
       this.descriptions = descriptions ;
   }

   public void setOnRemoveItemLister (removeItemListner listener) {
       this.listener = listener ;
   }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex) ;
        View inflater_ = inflater.inflate(R.layout.second_row_template, parent, false) ;

        return new myViewHolder(inflater_, listener) ;
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

        private FloatingActionButton cb;

        public myViewHolder(@NonNull View itemView, removeItemListner listner) {
            super(itemView);

            custom_challenges = (EditText) itemView.findViewById(R.id.challenge_name)  ;
            custom_descriptions = (EditText)itemView.findViewById(R.id.description_name)  ;
            cb = (FloatingActionButton) itemView.findViewById(R.id.complete_button) ;

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.setRemoveItem(getAdapterPosition());

                }
            });

        }
    }

}
