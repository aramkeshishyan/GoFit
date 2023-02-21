package com.example.gofit.data.model.responses;

import com.example.gofit.data.model.requests.Friends;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class defaultResponseFriendsList {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<Friends> data;
    @SerializedName("message")
    @Expose
    private String message;

    public defaultResponseFriendsList(boolean success, List<Friends> data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Friends> getData() {
        return data;
    }

    public void setData(List<Friends> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void printData(){
        System.out.println((isSuccess()));

        for (int i = 0; i < data.size();i++)
        {
            System.out.println(data.get(i));
        }

        System.out.println((getMessage()));
    }
}
