package com.example.gofit.data.model.requests;

public class UserStats {
    private int stepCount;
    private double totalDistanceKm;
    private int challengeCount;
    private int totalPoints;

    public UserStats(int stepCount, double totalDistanceKm, int challengeCount, int totalPoints) {
        this.stepCount = stepCount;
        this.totalDistanceKm = totalDistanceKm;
        this.challengeCount = challengeCount;
        this.totalPoints = totalPoints;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public double getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public void setTotalDistanceKm(double totalDistanceKm) {
        this.totalDistanceKm = totalDistanceKm;
    }

    public int getChallengeCount() {
        return challengeCount;
    }

    public void setChallengeCount(int challengeCount) {
        this.challengeCount = challengeCount;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
