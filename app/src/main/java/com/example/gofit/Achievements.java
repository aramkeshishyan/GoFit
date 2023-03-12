package com.example.gofit;

public class Achievements {
    private int id, steps, sign_ins;
    private String title, description;

    public Achievements(int id, int steps, int sign_ins, String title, String description) {
        this.id = id;
        this.steps = steps;
        this.sign_ins = sign_ins;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getSteps() {
        return steps;
    }

    public int getSign_ins() {
        return sign_ins;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
