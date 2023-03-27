package com.example.gofit.data.model.requests;

public class UserAcceptedDenied {

    private int RequestId;

    public UserAcceptedDenied(int requestId) {
        RequestId = requestId;
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }
}
