package com.example.custom_clothing_order_manager.models;

import java.util.List;

public class Tailors {
    private String id;
    private String name;
    private String email;
    private String password;
    private String shopName;
    private String specializations;
    private String imagePath;
    private double latitude;
    private double longitude;

    public Tailors() {
    }

    public Tailors(String id, String name, String email, String password, String shopName, String specializations, String imagePath, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.shopName = shopName;
        this.specializations = specializations;
        this.imagePath = imagePath;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
