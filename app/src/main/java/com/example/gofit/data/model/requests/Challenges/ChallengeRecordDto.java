package com.example.gofit.data.model.requests.Challenges;

import java.time.LocalDateTime;

public class ChallengeRecordDto {
    private String creatorEmail;
    private ChallengeDto challenge;
    private LocalDateTime dateAccepted;
    private LocalDateTime dateStarted;
    private LocalDateTime dateEnd;
    private LocalDateTime dateLastCompleted;
    private boolean isComplete;
    private int streak;
    private int totalDaysCompleted;
    private int score;

    public ChallengeRecordDto(String creatorEmail, ChallengeDto challenge, LocalDateTime dateAccepted, LocalDateTime dateStarted, LocalDateTime dateEnd, LocalDateTime dateLastCompleted, boolean isComplete, int streak, int totalDaysCompleted, int score) {
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

    public ChallengeDto getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDto challenge) {
        this.challenge = challenge;
    }

    public LocalDateTime getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(LocalDateTime dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateLastCompleted() {
        return dateLastCompleted;
    }

    public void setDateLastCompleted(LocalDateTime dateLastCompleted) {
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
