package com.example.custom_clothing_order_manager.models;

public class ClothingItem {
    private String name;
    private String category;
    private int imageResource;

    public ClothingItem(String name, String category,int imageResource) {
        this.name = name;
        this.category = category;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
