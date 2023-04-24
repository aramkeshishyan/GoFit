package com.example.gofit.data.model.requests.Challenges;

import com.example.gofit.Exercise_Item;

import java.util.ArrayList;

public class Challenge {

    private int id;
    private int creatorId;
    private String title;
    private String desc;
    private ArrayList<Exercise_Item> exerciseList;
    private int durationDays;
    private int reps;
    private int sets;
}
