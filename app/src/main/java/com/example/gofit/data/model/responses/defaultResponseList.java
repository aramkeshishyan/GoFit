package com.example.gofit.data.model.responses;

import java.util.ArrayList;

public class defaultResponseList<T> {

    private boolean success;
    private ArrayList<T> data;
    private String message;

    public defaultResponseList(boolean success, ArrayList<T> data, String message) {
        this.success = success;
        this.data = new ArrayList<>();
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<T> getData() {
        return data;
    }


    public void setData(ArrayList<T> data) {
        this.data = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
