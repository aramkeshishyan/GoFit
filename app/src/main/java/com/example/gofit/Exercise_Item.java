package com.example.gofit;

public class Exercise_Item {
    private String item_id;
    private String item_name;
    private String item_mGroup;
    private String item_level;
    private String item_description;
    private String item_image;



    public Exercise_Item(String item_id, String item_name, String item_mGroup, String item_level,  String item_description, String item_image) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_mGroup = item_mGroup;
        this.item_level = item_level;
        this.item_description = item_description;
        this.item_image = item_image;
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

}
