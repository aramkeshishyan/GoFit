package com.example.gofit;
public class Nutrition_Item {
    private String item_id;
    private String item_name;
    private String item_type;
    private String item_calories;
    private String item_proteins;
    private String item_carbs;
    private String item_fats;
    private String item_image;
    private String item_ingredients;

    public Nutrition_Item(String item_id, String item_name, String item_type, String item_calories, String item_proteins, String item_carbs, String item_fats, String item_image, String item_ingredients) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_calories = item_calories;
        this.item_proteins = item_proteins;
        this.item_carbs = item_carbs;
        this.item_fats = item_fats;
        this.item_image = item_image;
        this.item_ingredients = item_ingredients;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public String getItem_calories() {
        return item_calories;
    }

    public String getItem_proteins() {
        return item_proteins;
    }

    public String getItem_carbs() {
        return item_carbs;
    }

    public String getItem_fats() {
        return item_fats;
    }

    public String getItem_image() {
        return item_image;
    }

    public String getItem_ingredients() {
        return item_ingredients;
    }
}