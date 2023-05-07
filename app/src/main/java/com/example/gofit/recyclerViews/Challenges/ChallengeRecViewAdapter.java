package com.example.gofit.recyclerViews.Challenges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.R;

import java.util.ArrayList;

public class ChallengeRecViewAdapter extends RecyclerView.Adapter<ChallengeRecViewAdapter.ViewHolder> {

    private ArrayList<ChallengeRecordDto> challengeArrayList;

    private OnNoteListener mOnNoteListener;
    private Context challenge_context;

    public ChallengeRecViewAdapter(Context challenge_context, ArrayList<ChallengeRecordDto> challenges, OnNoteListener onNoteListener){
        this.challenge_context = challenge_context;
        this.challengeArrayList = challenges;
        this.mOnNoteListener = onNoteListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(challenge_context).inflate(R.layout.recycle_view_item_challenge1, parent,false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeRecViewAdapter.ViewHolder holder, int position) {
        ChallengeRecordDto challenge = challengeArrayList.get(position);
        holder.item_name.setText(challenge.getTitle());
        holder.item_description.setText(challenge.getDesc());
        holder.challengeScore.setText(String.valueOf(challenge.getScore()));
    }

    @Override
    public int getItemCount() { return challengeArrayList.size(); }

    public void setChallenges(ArrayList<ChallengeRecordDto> challenges){
        this.challengeArrayList = challenges;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private TextView item_name;
        private TextView item_description;
        private TextView challengeScore;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);

            item_name = itemView.findViewById(R.id.rv_name);
            item_description = itemView.findViewById(R.id.rv_description);
            challengeScore = itemView.findViewById(R.id.challengeScore);

            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) { onNoteListener.onNoteClick(getAdapterPosition()); }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
