package com.example.gofit.data.model.requests.Challenges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class ChallengeRecordDto {

    @SerializedName("creatorEmail")
    @Expose
    private String creatorEmail;
    @SerializedName("challenge")
    @Expose
    private ChallengeDtoInRecord challenge;
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

    public ChallengeRecordDto(String creatorEmail, ChallengeDtoInRecord challenge, String dateAccepted, String dateStarted, String dateEnd, String dateLastCompleted, boolean isComplete, int streak, int totalDaysCompleted, int score) {
        this.creatorEmail = creatorEmail;
        this.challenge = challenge;
        this.dateAccepted = dateAccepted;
        this.dateStarted = dateStarted;
        this.dateEnd = dateEnd;
        this.dateLastCompleted = dateLastCompleted;
        this.isComplete = isComplete;
        this.streak = streak;
        this.totalDaysCompleted = totalDaysCompleted;
        this.score = score;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public ChallengeDtoInRecord getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDtoInRecord challenge) {
        this.challenge = challenge;
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
