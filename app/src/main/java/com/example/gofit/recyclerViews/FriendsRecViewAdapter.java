package com.example.gofit.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gofit.Friend;
import com.example.gofit.R;

import java.util.ArrayList;

public class FriendsRecViewAdapter extends RecyclerView.Adapter<FriendsRecViewAdapter.ViewHolder> {

    private final FriendRecyclerViewInterface recyclerViewInterface;
    private ArrayList<Friend> friends = new ArrayList<>();

    private Context context;

    public FriendsRecViewAdapter(Context context, FriendRecyclerViewInterface recyclerViewInterface)
    {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
        notifyDataSetChanged(); //Notifies adapter when changes are made to contact list to refresh page
    }

    //Holds view UI elements for every list item in Recycler View
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView friendListImgV;
        private TextView friendListNameTxtV;
        private LinearLayout friendListItemParent; //So that functionality can be added when entire friend_list_item is clicked

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friendListImgV = itemView.findViewById(R.id.friendListImgV);
            friendListNameTxtV = itemView.findViewById(R.id.friendListNameTxtV);
            friendListItemParent = itemView.findViewById(R.id.friendListItemParent);
        }
    }


    //Generates ViewHolder instance
    @NonNull
    @Override
    public FriendsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //Functionality of onClick, setting text/img etc go here
    @Override
    public void onBindViewHolder(@NonNull FriendsRecViewAdapter.ViewHolder holder, int position) {
        holder.friendListNameTxtV.setText(friends.get(position).getName());

        holder.friendListItemParent.setOnClickListener(new View.OnClickListener() { //Show toast message with name of friend_list_item when clicked
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface !=null)
                {
                    int pos = holder.getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onFriendItemClick(pos);
                    }
                }
                //Toast.makeText(context, friends.get(holder.getAdapterPosition()).getName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        //Get user's profile pic or set default
        String imageUrl = friends.get(position).getImageURL();
        if (imageUrl.isEmpty()) {
            imageUrl = "https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg";
        }

        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .centerCrop()
                .into(holder.friendListImgV);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
