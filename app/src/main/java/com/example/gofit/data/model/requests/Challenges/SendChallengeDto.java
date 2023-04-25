package com.example.gofit.data.model.requests.Challenges;

public class SendChallengeDto {
    private int challengeId;
    private String friendEmail;

    public SendChallengeDto(int challengeId) {
        this.challengeId = challengeId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }
}
