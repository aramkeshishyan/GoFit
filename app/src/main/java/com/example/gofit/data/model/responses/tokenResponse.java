package com.example.gofit.data.model.responses;

import com.google.gson.annotations.SerializedName;

public class tokenResponse {

    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private String access_token;

    @SerializedName("message")
    private String message;

    public  tokenResponse(){

    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
