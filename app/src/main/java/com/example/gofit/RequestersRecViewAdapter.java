package com.example.gofit;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.gofit.data.model.requests.UserAcceptedDenied;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show();
                acceptCall(friends.get(holder.getAdapterPosition()).getRequestId());

            }
        });

        holder.friendListDenyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,  "Request Denied", Toast.LENGTH_SHORT).show();
                denyCall(friends.get(holder.getAdapterPosition()).getRequestId());
            }
        });



        //Glide allows easier image uploading from URL link
        //For testing purposes
        String imageUrl = friends.get(position).getPhotoUrl();
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

    //Executed When Accept Button Clicked
    public void acceptCall(int reqId)
    {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        UserAcceptedDenied acceptedUser = new UserAcceptedDenied(reqId);

        MainApplication.apiManager.acceptFriend(token, acceptedUser, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();
            }

            @Override
            public void onFailure(Call<defaultResponseList<Friend>> call, Throwable t) {

            }
        });


    }

    //Executed When Deny Button Clicked
    public void denyCall(int reqId)
    {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        UserAcceptedDenied deniedUser = new UserAcceptedDenied(reqId);

        MainApplication.apiManager.denyFriend(token, deniedUser, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {
                defaultResponse<String> denyResponse = response.body();
            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {

            }
        });


    }
}
