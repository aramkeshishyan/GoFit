package com.example.gofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ExerciseActivity extends AppCompatActivity {
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        getIncomingIntent();
        backButton = findViewById(R.id.ex_backButton);
        backButton.setOnClickListener(view -> ExerciseActivity.super.finish());
    }
    private void getIncomingIntent(){
        //if statement prevents the app from crashing when passing an intent
        if(getIntent().hasExtra("ex_id")
                && getIntent().hasExtra("ex_title")
                && getIntent().hasExtra("ex_group")
                && getIntent().hasExtra("ex_level")
                && getIntent().hasExtra("ex_description")
                && getIntent().hasExtra("ex_imageUrl")
                && getIntent().hasExtra("ex_type")){

            String id = getIntent().getStringExtra("ex_id");
            String title = getIntent().getStringExtra("ex_title");
            String group = getIntent().getStringExtra("ex_group");
            String level = getIntent().getStringExtra("ex_level");
            String description = getIntent().getStringExtra("ex_description");
            String imageUrl = getIntent().getStringExtra("ex_imageUrl");
            String type = getIntent().getStringExtra("ex_type");

            setupItems(id, title, group, level, description, imageUrl, type);
        }
    }

    private void setupItems(String id, String title, String group, String level, String description, String imageUrl, String type) {
        TextView mTitle = findViewById(R.id.ex_title);
        mTitle.setText(title);

        TextView mGroup = findViewById(R.id.ex_muscleGroup);
        mGroup.setText("Group\n" + group);

        TextView mLevel = findViewById(R.id.ex_level);
        mLevel.setText("Level\n" + level);

        TextView mDescription = findViewById(R.id.ex_description);
        mDescription.setText("Description\n\t" + description);

        ImageView mImage = findViewById(R.id.ex_image);
        Picasso.get().load(imageUrl).into(mImage);

        TextView mType = findViewById(R.id.ex_type);
        mType.setText("Type\n" + type);

    }
}