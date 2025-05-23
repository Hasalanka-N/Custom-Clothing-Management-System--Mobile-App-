package com.example.custom_clothing_order_manager.models;

public class Oder {
        private int id;
        private String itemType;
        private String material;
        private String color;
        private String note;
        private String name;
        private String phone;
        private String street;
        private String city;
        private String country;
        private int customerId;
        private int tailorId;
        private String date;
        private String measurement;
        private String status;

    public Oder(int id, String itemType, String material, String color, String note, String name,
                        String phone, String street, String city, String country, int customerId,
                        int tailorId, String date, String measurement, String status) {
        this.id = id;
        this.itemType = itemType;
        this.material = material;
        this.color = color;
        this.note = note;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.country = country;
        this.customerId = customerId;
        this.tailorId = tailorId;
        this.date = date;
        this.measurement = measurement;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTailorId() {
        return tailorId;
    }

    public void setTailorId(int tailorId) {
        this.tailorId = tailorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
