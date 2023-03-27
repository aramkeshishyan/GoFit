package com.example.gofit.data.model.requests;

public class RequestersInfo {
    private int requestId;
    private String fullName;
    private String photoUrl;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public RequestersInfo(int requestId, String fullName, String photoUrl) {
        this.requestId = requestId;
        this.fullName = fullName;
        this.photoUrl = photoUrl;


    }
}
