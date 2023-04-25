package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofit.R;
import com.example.gofit.data.model.requests.Challenges.ChallengeDto;

import java.util.ArrayList;

public class custum_base_adapter extends RecyclerView.Adapter<custum_base_adapter.myViewHolder> {

    private ArrayList<ChallengeDto> challenges_list ;

    private Context context ;

    public custum_base_adapter(Context context, ArrayList<ChallengeDto> challenges_list) {
        this.context = context ;
        this.challenges_list = challenges_list ;
    }

    public void setChallenges_list (ArrayList<ChallengeDto> challenges_list) {
        this.challenges_list = challenges_list ;
        notifyDataSetChanged();
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


        holder.challenge_name.setText(challenges_list.get(position).getTitle());
        holder.description_name.setText(challenges_list.get(position).getDesc());
    }



    @Override
    public int getItemCount() {
        return challenges_list.size();
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
