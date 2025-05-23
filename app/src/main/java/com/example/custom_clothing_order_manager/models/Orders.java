package com.example.custom_clothing_order_manager.models;

public class Orders {
    private String id;
    private String itemType;
    private String material;
    private String color;
    private String note;
    private String cusName;
    private String phone;
    private String street;
    private String city;
    private String country;
    private String customerId;
    private String tailorId;
    private String date;
    private String measurement;
    private String status;

    public Orders() {
    }

    public Orders(String id, String itemType, String material, String color, String note, String cusName, String phone, String street, String city, String country, String customerId, String tailorId, String date, String measurement, String status) {
        this.id = id;
        this.itemType = itemType;
        this.material = material;
        this.color = color;
        this.note = note;
        this.cusName = cusName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCusName() {
        return cusName;
    }

    public void getCusName(String cusName) {
        this.cusName = cusName;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTailorId() {
        return tailorId;
    }

    public void setTailorId(String tailorId) {
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
