package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gofit.R;
import com.example.gofit.data.model.requests.Challenges.ChallengeRequestDto;

import java.util.ArrayList;


public class ChallengeRequestersRecViewAdapter extends RecyclerView.Adapter<ChallengeRequestersRecViewAdapter.ViewHolder>  {

    private ArrayList<ChallengeRequestDto> challenges = new ArrayList();
    private OnChallengeRequestActionListener requestActionListener;
    private Context context;

    public ChallengeRequestersRecViewAdapter(Context context, ChallengeRequestersRecViewAdapter.OnChallengeRequestActionListener listener) {
        this.context = context;
        requestActionListener = listener;
    }

    public void setChallengers(ArrayList<ChallengeRequestDto> challenges) {
        this.challenges = challenges;
        notifyDataSetChanged(); //Notifies adapter when changes are made to contact list to refresh page
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView challengeTitle;
        private ImageView challengeListImgV;
        private TextView challengeListNameTxtV;
        private CardView challengeListItemParent; //So that functionality can be added when entire friend_list_item is clicked

        private Button challengeListAcceptBtn;
        private Button challengeListDenyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            challengeTitle = itemView.findViewById(R.id.chal_title);
            challengeListImgV = itemView.findViewById(R.id.challengeRequestListImgV);
            challengeListNameTxtV = itemView.findViewById(R.id.challengeRequestListNameTxtV);
            challengeListItemParent = itemView.findViewById(R.id.challengeRequestListItemParent);

            challengeListAcceptBtn = itemView.findViewById(R.id.challengeRequestListAcceptBtn);
            challengeListDenyBtn = itemView.findViewById(R.id.challengeRequestListDenyBtn);

        }
    }

    //Generates ViewHolder instance
    @NonNull
    @Override
    public ChallengeRequestersRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_request_item, parent, false);
        ChallengeRequestersRecViewAdapter.ViewHolder holder = new ChallengeRequestersRecViewAdapter.ViewHolder(view);

        return holder;
    }

    //Functionality of onClick, setting text/img etc go here
    @Override
    public void onBindViewHolder(@NonNull ChallengeRequestersRecViewAdapter.ViewHolder holder, int position) {
        ChallengeRequestDto challengeRequest = challenges.get(position);

        holder.challengeTitle.setText(challengeRequest.getChallenge().getTitle());

        holder.challengeListNameTxtV.setText(challengeRequest.getCreatorName());

        //Glide allows easier image uploading from URL link
        //For testing purposes
        String imageUrl = challengeRequest.getCreatorPhotoURL();
        if (imageUrl.isEmpty()) {
            imageUrl = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .centerCrop()
                .into(holder.challengeListImgV);

        holder.challengeListItemParent.setOnClickListener(new View.OnClickListener() { //Show toast message with name of friend_list_item when clicked
            @Override
            public void onClick(View view) {
                Toast.makeText(context, challengeRequest.getChallenge().getTitle() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.challengeListAcceptBtn.setOnClickListener(view ->
                requestActionListener.onChallengeRequestAccepted(challengeRequest));

        holder.challengeListDenyBtn.setOnClickListener(view ->
                requestActionListener.onChallengeRequestDenied(challengeRequest));

    }


    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public interface OnChallengeRequestActionListener {
        void onChallengeRequestAccepted(ChallengeRequestDto request);

        void onChallengeRequestDenied(ChallengeRequestDto request);
    }
}
