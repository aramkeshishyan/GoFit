package com.example.gofit.data.model.requests;

public class UpdateSurvey {

    private int baseCalories;
    private int recCalories;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private String activityLvl;
    private String bodyType;
    private String goal;

    public UpdateSurvey(int baseCalories, int recCalories, int age, double weight, double height, String gender, String activityLvl, String bodyType, String goal) {
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
