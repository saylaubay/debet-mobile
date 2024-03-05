package com.example.debet_app_mobile.payload;

public class BuyDtos {

    private int id;

    private String productName;

    private int workerId;

    private double price;

    private int clientId;

    private double percent;

    private Integer part;

    public BuyDtos(String productName, int workerId, double price, int clientId, double percent, Integer part) {
        this.productName = productName;
        this.workerId = workerId;
        this.price = price;
        this.clientId = clientId;
        this.percent = percent;
        this.part = part;
    }

    public BuyDtos(String productName, double price, double percent, Integer part) {
        this.productName = productName;
        this.price = price;
        this.percent = percent;
        this.part = part;
    }

    public BuyDtos(String productName, double price, int clientId, double percent, Integer part) {
        this.productName = productName;
        this.price = price;
        this.clientId = clientId;
        this.percent = percent;
        this.part = part;
    }

    public BuyDtos() {
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

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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
}
