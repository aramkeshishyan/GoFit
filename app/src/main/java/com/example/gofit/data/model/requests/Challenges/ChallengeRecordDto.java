package com.example.gofit.data.model.requests.Challenges;

import com.example.gofit.Exercise_Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChallengeRecordDto {

    @SerializedName("challengeId")
    @Expose
    private int challengeId;
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
    @SerializedName("creatorEmail")
    @Expose
    private String creatorEmail;
    @SerializedName("dateAccepted")
    @Expose
    private String dateAccepted;
    @SerializedName("dateStarted")
    @Expose
    private String dateStarted;
    @SerializedName("dateEnd")
    @Expose
    private String dateEnd;
    @SerializedName("dateLastCompleted")
    @Expose
    private String dateLastCompleted;
    @SerializedName("isComplete")
    @Expose
    private boolean isComplete;
    @SerializedName("streak")
    @Expose
    private int streak;
    @SerializedName("totalDaysCompleted")
    @Expose
    private int totalDaysCompleted;
    @SerializedName("score")
    @Expose
    private int score;


    public ChallengeRecordDto(int challengeId, String title, String desc, ArrayList<Exercise_Item> exerciseList, int durationDays, int reps, int sets, String creatorEmail, String dateAccepted, String dateStarted, String dateEnd, String dateLastCompleted, boolean isComplete, int streak, int totalDaysCompleted, int score) {
        this.challengeId = challengeId;
        this.title = title;
        this.desc = desc;
        this.exerciseList = exerciseList;
        this.durationDays = durationDays;
        this.reps = reps;
        this.sets = sets;
        this.creatorEmail = creatorEmail;
        this.dateAccepted = dateAccepted;
        this.dateStarted = dateStarted;
        this.dateEnd = dateEnd;
        this.dateLastCompleted = dateLastCompleted;
        this.isComplete = isComplete;
        this.streak = streak;
        this.totalDaysCompleted = totalDaysCompleted;
        this.score = score;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
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

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(String dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateLastCompleted() {
        return dateLastCompleted;
    }

    public void setDateLastCompleted(String dateLastCompleted) {
        this.dateLastCompleted = dateLastCompleted;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getTotalDaysCompleted() {
        return totalDaysCompleted;
    }

    public void setTotalDaysCompleted(int totalDaysCompleted) {
        this.totalDaysCompleted = totalDaysCompleted;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
