package com.example.gofit.data.model.requests;

public class UpdateUserDto {
    private String fullName;
    private String email;
    private String photoUrl;

    public UpdateUserDto(String fullName, String email, String photoUrl) {
        this.fullName = fullName;
        this.email = email;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
