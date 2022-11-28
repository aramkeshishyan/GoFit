package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class FriendsListPage extends AppCompatActivity {

    private RecyclerView friendsRecyclerView;
    private SearchView searchViewFriendsList;
    private FriendsRecViewAdapter adapter;

    //Fake friends list for testing purposes
    private ArrayList<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list_page);

        searchViewFriendsList = findViewById(R.id.searchViewFriendsList);
        searchViewFriendsList.clearFocus(); //removes cursor from search view initially
        searchViewFriendsList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        friends = new ArrayList<>();
        //For loop to multiply fake friends to test scrolling
        for (int i = 0; i < 2; i++) {
            friends.add(new Friend("John Smith", "https://st.depositphotos.com/1269204/1219/i/600/depositphotos_12196477-stock-photo-smiling-men-isolated-on-the.jpg"));
            friends.add(new Friend("Jane Doe", "https://image.shutterstock.com/image-photo/indoor-portrait-beautiful-brunette-young-260nw-640005220.jpg"));
            friends.add(new Friend("Margot Robbie", "https://assets.vogue.com/photos/5cf7ed4504f90a017a26d60f/master/pass/5-things-to-know-about-margot-robbie.jpg"));
            friends.add(new Friend("Scarlette Johanson", "https://m.media-amazon.com/images/M/MV5BMTM3OTUwMDYwNl5BMl5BanBnXkFtZTcwNTUyNzc3Nw@@._V1_UY1200_CR180,0,630,1200_AL_.jpg"));
            friends.add(new Friend("Ryan Gosling", "https://upload.wikimedia.org/wikipedia/commons/f/f6/Ryan_Gosling_in_2018.jpg"));
            friends.add(new Friend("Adam Sandler", "https://cdn.britannica.com/24/157824-050-D8E9E191/Adam-Sandler-2011.jpg"));
            friends.add(new Friend("Emma Watson", "https://upload.wikimedia.org/wikipedia/commons/7/7f/Emma_Watson_2013.jpg"));
            friends.add(new Friend("Mark Zuckerberg", "https://cdn.britannica.com/99/236599-050-1199AD2C/Mark-Zuckerberg-2019.jpg"));
        }

        adapter = new FriendsRecViewAdapter(this);
        adapter.setFriends(friends);

        friendsRecyclerView = findViewById(R.id.friendsListPageRecView);
        friendsRecyclerView.setAdapter(adapter);
        friendsRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

    }

    private void filterList(String s) { //s is text searched in searchView
        ArrayList<Friend> filteredFriendsList = new ArrayList<>();
        for (Friend friend : friends) {
            if (friend.getName().toLowerCase().contains(s.toLowerCase())) {
                filteredFriendsList.add(friend);
            }
        }

        if (filteredFriendsList.isEmpty()) {
            Toast.makeText(this, "No friends found.", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFriends(filteredFriendsList); //update data in recyclerView to show searched data.
        }
    }
}