package com.example.gofit.data.model.requests;

public class UserUpdatePasswordDto {

    String email;
    String currPassword;
    String newPassword;

    public UserUpdatePasswordDto(String email, String currPassword, String newPassword) {
        this.email = email;
        this.currPassword = currPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrPassword() {
        return currPassword;
    }

    public void setCurrPassword(String currPassword) {
        this.currPassword = currPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
