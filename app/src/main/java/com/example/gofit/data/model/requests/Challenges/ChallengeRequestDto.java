package com.example.gofit.data.model.requests.Challenges;

public class ChallengeRequestDto {
    private int requestId;
    private ChallengeDto challenge;
    private String creatorName;
    private String creatorPhotoURL;

    public ChallengeRequestDto(int requestId, ChallengeDto challenge, String creatorName, String creatorPhotoURL) {
        this.requestId = requestId;
        this.challenge = challenge;
        this.creatorName = creatorName;
        this.creatorPhotoURL = creatorPhotoURL;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public ChallengeDto getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDto challenge) {
        this.challenge = challenge;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorPhotoURL() {
        return creatorPhotoURL;
    }

    public void setCreatorPhotoURL(String creatorPhotoURL) {
        this.creatorPhotoURL = creatorPhotoURL;
    }
}
