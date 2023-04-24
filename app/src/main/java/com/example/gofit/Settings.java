package com.example.gofit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the ImageButton using its ID
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Settings.this, UserProfile.class);
//                startActivity(intent);
                  finish();
            }
        });

        // Get references to the views
        ImageView profilePicture = findViewById(R.id.profilePicture);
        Button uploadButton = findViewById(R.id.uploadButton);
        TextView nameText = findViewById(R.id.nameText);
        Button changeNameButton = findViewById(R.id.changeNameButton);

        // Set OnClickListener on the uploadButton
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open the Gallery or Camera app
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                // Start the activity to select an image
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        // Set OnClickListener on the changeNameButton
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a dialog to get the new name from the user
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Enter Your Name");

                final EditText input = new EditText(Settings.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = input.getText().toString();
                        // Update the text of the TextView with the new name
                        nameText.setText(newName);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    // Handle the result from the Gallery or Camera app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Set the selected image as the profile picture
            ImageView profilePicture = findViewById(R.id.profilePicture);
            Uri imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }
}
