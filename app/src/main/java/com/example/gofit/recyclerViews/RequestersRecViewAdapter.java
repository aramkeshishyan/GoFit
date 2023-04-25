package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gofit.R;
import com.example.gofit.data.model.requests.RequestersInfo;

import java.util.ArrayList;


public class RequestersRecViewAdapter extends RecyclerView.Adapter<RequestersRecViewAdapter.ViewHolder>{
    private ArrayList<RequestersInfo> friends = new ArrayList<>();
    private OnFriendRequestActionListener requestActionListener;
    private Context context;

    public RequestersRecViewAdapter(Context context, OnFriendRequestActionListener listener) {
        this.context = context;
        requestActionListener = listener;
    }

    public void setFriends(ArrayList<RequestersInfo> friends) {
        this.friends = friends;
        notifyDataSetChanged(); //Notifies adapter when changes are made to contact list to refresh page
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView friendListImgV;
        private TextView friendListNameTxtV;
        private LinearLayout friendListItemParent; //So that functionality can be added when entire friend_list_item is clicked

        private Button friendListAcceptBtn;
        private Button friendListDenyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friendListImgV = itemView.findViewById(R.id.friendRequestListImgV);
            friendListNameTxtV = itemView.findViewById(R.id.friendRequestListNameTxtV);
            friendListItemParent = itemView.findViewById(R.id.friendRequestListItemParent);

            friendListAcceptBtn = itemView.findViewById(R.id.friendRequestListAcceptBtn);
            friendListDenyBtn = itemView.findViewById(R.id.friendRequestListDenyBtn);

        }
    }


    //Generates ViewHolder instance
    @NonNull
    @Override
    public RequestersRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_list_item, parent, false);
        RequestersRecViewAdapter.ViewHolder holder = new RequestersRecViewAdapter.ViewHolder(view);

        return holder;
    }

    //Functionality of onClick, setting text/img etc go here
    @Override
    public void onBindViewHolder(@NonNull RequestersRecViewAdapter.ViewHolder holder, int position) {
        RequestersInfo friendRequest = friends.get(position);

        holder.friendListNameTxtV.setText(friendRequest.getFullName());

        //Glide allows easier image uploading from URL link
        //For testing purposes
        String imageUrl = friendRequest.getPhotoUrl();
        if (imageUrl.isEmpty()) {
            imageUrl = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .centerCrop()
                .into(holder.friendListImgV);

        holder.friendListItemParent.setOnClickListener(new View.OnClickListener() { //Show toast message with name of friend_list_item when clicked
            @Override
            public void onClick(View view) {
                Toast.makeText(context, friendRequest.getFullName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.friendListAcceptBtn.setOnClickListener(view ->
                    requestActionListener.onFriendRequestAccepted(friendRequest));

        holder.friendListDenyBtn.setOnClickListener(view ->
                requestActionListener.onFriendRequestDenied(friendRequest));

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public interface OnFriendRequestActionListener {
        void onFriendRequestAccepted(RequestersInfo request);

        void onFriendRequestDenied(RequestersInfo request);
    }
}
