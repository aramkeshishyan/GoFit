package com.example.gofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class custum_base_adapter extends RecyclerView.Adapter<custum_base_adapter.myViewHolder> {

    private ArrayList<String> string_names  ;

    private ArrayList<String> description_namess;
    private Context context ;

    public custum_base_adapter(Context context, ArrayList<String> string_names, ArrayList<String> description_namess) {
        this.context = context ;
        this.string_names = string_names ;
        this.description_namess = description_namess ;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context) ;
        View inflate = inflater.inflate(R.layout.row_template_1, parent, false) ;

        return new myViewHolder(inflate) ;


    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.challenge_name.setText(string_names.get(position));
        holder.description_name.setText(description_namess.get(position));
    }



    @Override
    public int getItemCount() {
        return string_names.size();
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView challenge_name ;

        private TextView description_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            challenge_name = (TextView) itemView.findViewById(R.id.challenge_name) ;
            description_name = (TextView)itemView.findViewById(R.id.description_name) ;
        }
    }
}
