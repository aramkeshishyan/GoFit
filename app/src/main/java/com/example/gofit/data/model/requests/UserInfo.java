package com.example.gofit.data.model.requests;

public class UserInfo {


    private String fullName;
    private String email;
    private String photoUrl;
    private boolean surveyComplete;
    private int baseCalories;
    private int recCalories;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private String activityLvl;
    private String bodyType;
    private String goal;

    public UserInfo(String fullName, String email, String photoUrl, boolean surveyComplete,
                    int baseCalories, int recCalories, int age, double weight, double height,
                    String gender, String activityLvl, String bodyType, String goal) {
        this.fullName = fullName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.surveyComplete = surveyComplete;
        this.baseCalories = baseCalories;
        this.recCalories = recCalories;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activityLvl = activityLvl;
        this.bodyType = bodyType;
        this.goal = goal;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isSurveyComplete() {
        return surveyComplete;
    }

    public void setSurveyComplete(boolean surveyComplete) {
        this.surveyComplete = surveyComplete;
    }

    public int getBaseCalories() {
        return baseCalories;
    }

    public void setBaseCalories(int baseCalories) {
        this.baseCalories = baseCalories;
    }

    public int getRecCalories() {
        return recCalories;
    }

    public void setRecCalories(int recCalories) {
        this.recCalories = recCalories;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActivityLvl() {
        return activityLvl;
    }

    public void setActivityLvl(String activityLvl) {
        this.activityLvl = activityLvl;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
