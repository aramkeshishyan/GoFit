package com.example.gofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrition_Item {

    @SerializedName("id")
    @Expose
    private String item_id;
    @SerializedName("title")
    @Expose
    private String item_name;
    @SerializedName("type")
    @Expose
    private String item_type;
    @SerializedName("calories")
    @Expose
    private String item_calories;
    @SerializedName("proteins")
    @Expose
    private String item_proteins;
    @SerializedName("carbs")
    @Expose
    private String item_carbs;
    @SerializedName("fats")
    @Expose
    private String item_fats;
    @SerializedName("photoUrl")
    @Expose
    private String item_image;
    @SerializedName("desc")
    @Expose
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