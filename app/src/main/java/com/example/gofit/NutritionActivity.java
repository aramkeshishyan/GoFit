package com.example.gofit;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NutritionActivity extends AppCompatActivity {
    private ImageButton backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        getIncomingIntent();
        backButton = findViewById(R.id.nt_backButton);
        backButton.setOnClickListener(view -> NutritionActivity.super.finish());
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("nt_id")
        && getIntent().hasExtra("nt_title")
        && getIntent().hasExtra("nt_type")
        && getIntent().hasExtra("nt_calories")
        && getIntent().hasExtra("nt_proteins")
        && getIntent().hasExtra("nt_carbs")
        && getIntent().hasExtra("nt_fats")
        && getIntent().hasExtra("nt_image")
        && getIntent().hasExtra("nt_ingredients")){
            String id = getIntent().getStringExtra("nt_id");
            String title = getIntent().getStringExtra("nt_title");
            String type = getIntent().getStringExtra("nt_type");
            String calories = getIntent().getStringExtra("nt_calories");
            String proteins = getIntent().getStringExtra("nt_proteins");
            String carbs = getIntent().getStringExtra("nt_carbs");
            String fats = getIntent().getStringExtra("nt_fats");
            String image = getIntent().getStringExtra("nt_image");
            String ingredients = getIntent().getStringExtra("nt_ingredients");
            setupItems(id, title, type, calories, proteins, carbs, fats, image, ingredients);
        }
    }

    private void setupItems(String id, String title, String type, String calories, String proteins, String carbs, String fats, String image, String ingredients) {
        TextView nTitle = findViewById(R.id.nt_title);
        nTitle.setText(title);

        TextView nType = findViewById(R.id.nt_type);
        nType.setText(type);

        TextView nCalories = findViewById(R.id.nt_calories);
        nCalories.setText("Calories\n" + calories);

        TextView nProteins = findViewById(R.id.nt_proteins);
        nProteins.setText("Proteins\n" + proteins + "g");

        TextView nCarbs = findViewById(R.id.nt_carbs);
        nCarbs.setText("Carbs\n" + carbs + "g");

        TextView nFats = findViewById(R.id.nt_fats);
        nFats.setText("Fats\n" + fats + "g");

        ImageView nImage = findViewById(R.id.nt_image);
        Picasso.get().load(image).into(nImage);

        TextView nIngredients = findViewById(R.id.nt_ingredients);
        nIngredients.setText("Ingredients: \n" + ingredients);
    }
}
