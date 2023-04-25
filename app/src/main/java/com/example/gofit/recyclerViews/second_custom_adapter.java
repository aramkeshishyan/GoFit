package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.StackView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofit.R;
import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.removeItemListner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class second_custom_adapter extends RecyclerView.Adapter<second_custom_adapter.myViewHolder> {

    private ArrayList<ChallengeRecordDto> challenges ;

   private FloatingActionButton cb ;

   private removeItemListner listener ;
   private Context contex;

   public second_custom_adapter(Context context, ArrayList<ChallengeRecordDto> challenges) {
       this.contex = context ;
       this.challenges = challenges ;

   }


   public void setChallengesList (ArrayList<ChallengeRecordDto> challenges) {
       this.challenges = challenges ;
       notifyDataSetChanged();
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
       // fake test, so I be able to push At the moment //
       holder.custom_challenges.setText("fake");
        holder.custom_descriptions.setText("fake");

        // ??? Im not sure what causing the error here      ///
        //holder.custom_descriptions.setText(challenges.get(position).getChallenge().desc);
       // holder.custom_challenges.setText(challenges.get(position).getChallenge().title);


    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView custom_challenges ;
        private TextView custom_descriptions;

        private FloatingActionButton cb;

        public myViewHolder(@NonNull View itemView, removeItemListner listner) {
            super(itemView);

            custom_challenges = (TextView) itemView.findViewById(R.id.challenge_name)  ;
            custom_descriptions = (TextView)itemView.findViewById(R.id.description_name)  ;
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
