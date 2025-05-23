package com.example.custom_clothing_order_manager.models;

public class Messages {
    private String id;
    private String cusId;
    private String tailorId;
    private String message;
    private String timestamp;
    private String senderRole;

    public Messages() {
    }

    public Messages(String id, String cusId, String tailorId, String message, String timestamp, String senderRole) {
        this.id = id;
        this.cusId = cusId;
        this.tailorId = tailorId;
        this.message = message;
        this.timestamp = timestamp;
        this.senderRole = senderRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getTailorId() {
        return tailorId;
    }

    public void setTailorId(String tailorId) {
        this.tailorId = tailorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }
}
