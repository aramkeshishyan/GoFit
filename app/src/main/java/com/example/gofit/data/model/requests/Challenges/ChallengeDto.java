package com.example.gofit.data.model.requests.Challenges;

import com.example.gofit.Exercise_Item;

import java.util.ArrayList;

public class ChallengeDto {
    private int challengeId;
    private String creatorEmail;
    private String title;
    private String desc;
    private ArrayList<Exercise_Item> exerciseList = new ArrayList<>();
    private int durationDays;
    private int reps;
    private int sets;

    public ChallengeDto(int challengeId, String creatorEmail, String title, String desc, ArrayList<Exercise_Item> exerciseList, int durationDays, int reps, int sets) {
        this.challengeId = challengeId;
        this.creatorEmail = creatorEmail;
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

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
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
