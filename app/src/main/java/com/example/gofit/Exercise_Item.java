package com.example.gofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exercise_Item {
    @SerializedName("id")
    @Expose
    private String item_id;
    @SerializedName("title")
    @Expose
    private String item_name;
    @SerializedName("muscleGroup")
    @Expose
    private String item_mGroup;
    @SerializedName("exerciseLvl")
    @Expose
    private String item_level;
    @SerializedName("desc")
    @Expose
    private String item_description;
    @SerializedName("photoURL")
    @Expose
    private String item_image;

    @SerializedName("type")
    @Expose
    private String item_type;




    public Exercise_Item(String item_id, String item_name, String item_mGroup, String item_level,  String item_description, String item_image, String item_type) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_mGroup = item_mGroup;
        this.item_level = item_level;
        this.item_description = item_description;
        this.item_image = item_image;
        this.item_type = item_type;
    }

    public String getItem_id() { return item_id; }
    public String getItem_name() {
        return item_name;
    }
    public String getItem_mGroup() { return item_mGroup; }
    public String getItem_level() { return item_level; }
    public String getItem_description() {
        return item_description;
    }

    public String getItem_image() {
        return item_image;
    }
    public String getItem_type() { return item_type;}

}
