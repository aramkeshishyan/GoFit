package com.example.gofit.data.model.requests;

public class Friends {

    private String fullName;
    private String email;
    private String imageURL;

    public Friends(String fullName, String email, String imageUrl) {
        this.fullName = fullName;
        this.email = email;
        this.imageURL = imageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageUrl(String imageURL) {
        this.imageURL = imageURL;
    }
}
