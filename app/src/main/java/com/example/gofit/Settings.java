package com.example.gofit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Toast;

import com.example.gofit.data.model.requests.UserUpdatePasswordDto;
import com.example.gofit.data.model.responses.defaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private TextView nameText;
    private ImageButton backButton;
    private ImageView profilePicture;
    private Button uploadButton;
    private Button changeNameButton;
    private Button changePasswordBtn;

    private String userEmail;
    private String currentPassword;
    private String newPassword;

    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        profilePicture = findViewById(R.id.profilePicture);

        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(this);

        nameText = findViewById(R.id.nameText);

        changeNameButton = findViewById(R.id.changeNameButton);
        changeNameButton.setOnClickListener(this);

        changePasswordBtn = findViewById(R.id.changePasswordButton);
        changePasswordBtn.setOnClickListener(this);

        sp = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

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


    private void changePassword()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

        builder.setCancelable(true);
        builder.setTitle("Change Password");
        builder.setMessage("Please Fill in the Fields");

        EditText edtCurPass = new EditText(Settings.this);
        EditText edtNewPass = new EditText(Settings.this);
        EditText edtNewPassConf = new EditText(Settings.this);

        edtCurPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtNewPassConf.setTransformationMethod(PasswordTransformationMethod.getInstance());


        LinearLayout lp = new LinearLayout(Settings.this.getBaseContext());
        lp.setOrientation(LinearLayout.VERTICAL);
        lp.addView(edtCurPass);
        lp.addView(edtNewPass);
        lp.addView(edtNewPassConf);

        edtCurPass.setHint("Current Password");
        edtNewPass.setHint("New Password");
        edtNewPassConf.setHint("Confirm Password");

        builder.setView(lp);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userEmail = sp.getString("email", "");
                currentPassword = edtCurPass.getText().toString().trim();
                newPassword = edtNewPass.getText().toString().trim();

                UserUpdatePasswordDto updatePasswordDto = new UserUpdatePasswordDto(userEmail, currentPassword, newPassword);
                changePasswordCall(updatePasswordDto);


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        edtCurPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edtCurPass.length() == 0)
                {
                    edtCurPass.setError("Can't be Empty");
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else
                {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }

            }
        });

        edtNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edtNewPass.length() < 6)
                {
                    edtNewPass.setError("At least 6 characters");
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else if(!edtNewPassConf.getText().toString().trim().equals(edtNewPass.getText().toString().trim()))
                {
                    edtNewPassConf.setError("Passwords don't match");
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else if(edtCurPass.length() == 0)
                {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else if(edtNewPassConf.getText().toString().trim().equals(edtNewPass.getText().toString().trim()))
                {
                    edtNewPassConf.setError(null);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        edtNewPassConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edtNewPassConf.getText().toString().trim().equals(edtNewPass.getText().toString().trim()))
                {
                    edtNewPassConf.setError("Passwords don't match");
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else if(edtNewPass.length() < 6)
                {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else if(edtCurPass.length() == 0)
                {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else
                {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

    }

    private void changePasswordCall(UserUpdatePasswordDto updatePasswordDto)
    {
        String token = sp.getString("token", "");

        MainApplication.apiManager.updatePassword(token, updatePasswordDto, new Callback<defaultResponse<String>>() {
            @Override
            public void onResponse(Call<defaultResponse<String>> call, Response<defaultResponse<String>> response) {
                defaultResponse<String> updateResponse = response.body();

                if(updateResponse.isSuccess())
                {
                    Toast.makeText(Settings.this, "Password Updated", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Settings.this, updateResponse.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponse<String>> call, Throwable t) {
                Toast.makeText(Settings.this,
                        "Error: ", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadButton:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                // Start the activity to select an image
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
            case R.id.changePasswordButton:
                changePassword();
                break;
            case R.id.backButton:
                super.finish();
                break;
            case R.id.changeNameButton:
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
    }
}
