package com.example.gofit.data.model.requests.Challenges;

import com.example.gofit.Exercise_Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChallengeDtoInRecord {

    @SerializedName("id")
    @Expose
    private int challengeId;
    @SerializedName("creatorId")
    @Expose
    private int creatorId;        //Instead of private String creatorEmail;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("exerciseList")
    @Expose
    private ArrayList<Exercise_Item> exerciseList = new ArrayList<>();
    @SerializedName("durationDays")
    @Expose
    private int durationDays;
    @SerializedName("reps")
    @Expose
    private int reps;
    @SerializedName("sets")
    @Expose
    private int sets;

    public ChallengeDtoInRecord(int challengeId, int creatorId, String title, String desc, ArrayList<Exercise_Item> exerciseList, int durationDays, int reps, int sets) {
        this.challengeId = challengeId;
        this.creatorId = creatorId;
        this.title = title;
        this.desc = desc;
        this.exerciseList = exerciseList;
        this.durationDays = durationDays;
        this.reps = reps;
        this.sets = sets;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<Exercise_Item> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise_Item> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
