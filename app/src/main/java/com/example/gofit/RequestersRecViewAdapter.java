package com.example.gofit;

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
import com.example.gofit.data.model.requests.RequestersInfo;

import java.util.ArrayList;

public class RequestersRecViewAdapter extends RecyclerView.Adapter<RequestersRecViewAdapter.ViewHolder>{

    private ArrayList<RequestersInfo> friends = new ArrayList<>();

    private Context context;

    public RequestersRecViewAdapter(Context context) { this.context = context; }

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
        holder.friendListNameTxtV.setText(friends.get(position).getFullName());

        holder.friendListItemParent.setOnClickListener(new View.OnClickListener() { //Show toast message with name of friend_list_item when clicked
            @Override
            public void onClick(View view) {
                Toast.makeText(context, friends.get(holder.getAdapterPosition()).getFullName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.friendListAcceptBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Accept selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.friendListDenyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Deny selected", Toast.LENGTH_SHORT).show();
            }
        });



        //Glide allows easier image uploading from URL link
        //For testing purposes
        Glide.with(context)
                .asBitmap()
                .load(friends.get(position).getPhotoUrl())
                .centerCrop()
                .into(holder.friendListImgV);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
