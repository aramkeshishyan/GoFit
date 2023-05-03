package com.example.gofit.data.model.requests.Challenges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendChallengeDto {
    @SerializedName("challengeId")
    @Expose
    private int challengeId;
    @SerializedName("friendUserEmail")
    @Expose
    private String friendEmail;

    public SendChallengeDto(int challengeId, String friendEmail) {
        this.challengeId = challengeId;
        this.friendEmail = friendEmail;
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
