package com.example.gofit.data.model.requests.Challenges;

import com.example.gofit.Exercise_Item;

import java.util.ArrayList;

public class Challengess {

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;
    public int creatorId;
    public String title;
    public String desc;
    private ArrayList<Exercise_Item> exerciseList;
    private int durationDays;
    private int reps;
    private int sets;
}
