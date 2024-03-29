package com.example.gofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Friend implements Serializable {

    @SerializedName("fullName")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    public Friend(String name, String email, String imageURL) {
        this.name = name;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
