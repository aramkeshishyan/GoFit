package com.example.gofit.data.model.requests.Challenges;

import java.util.ArrayList;

public class CreateChallengeDto {

    private String creatorEmail;
    private String title;
    private String desc;
    private ArrayList<Integer> exerciseIdList;
    private int durationDays;
    private int reps;
    private int sets;

    public CreateChallengeDto(String creatorEmail, String title, String desc, ArrayList<Integer> exerciseIdList, int durationDays, int reps, int sets) {
        this.creatorEmail = creatorEmail;
        this.title = title;
        this.desc = desc;
        this.exerciseIdList = exerciseIdList;
        this.durationDays = durationDays;
        this.reps = reps;
        this.sets = sets;
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

    public ArrayList<Integer> getExerciseIdList() {
        return exerciseIdList;
    }

    public void setExerciseIdList(ArrayList<Integer> exerciseIdList) {
        this.exerciseIdList = exerciseIdList;
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
