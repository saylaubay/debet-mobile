package com.example.debet_app_mobile.payload;

import java.sql.Timestamp;

public class BuyDtoss {

    private int id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String productName;

    private UserDtos worker;

    private double price;

    private ClientDtos client;

    private double percent;

    private Integer part;

    private boolean enabled;

    public BuyDtoss(String productName, UserDtos worker, double price, ClientDtos client, double percent, Integer part) {
        this.productName = productName;
        this.worker = worker;
        this.price = price;
        this.client = client;
        this.percent = percent;
        this.part = part;
    }

    public BuyDtoss(String productName, double price, double percent, Integer part) {
        this.productName = productName;
        this.price = price;
        this.percent = percent;
        this.part = part;
    }

    public BuyDtoss(String productName, double price, ClientDtos client, double percent, Integer part) {
        this.productName = productName;
        this.price = price;
        this.client = client;
        this.percent = percent;
        this.part = part;
    }

    public BuyDtoss() {
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public UserDtos getWorker() {
        return worker;
    }

    public void setWorker(UserDtos worker) {
        this.worker = worker;
    }

    public ClientDtos getClient() {
        return client;
    }

    public void setClient(ClientDtos client) {
        this.client = client;
    }
}
