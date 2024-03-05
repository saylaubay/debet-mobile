package com.example.debet_app_mobile.payload;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContractDto implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private Timestamp createdAt;

    @SerializedName("updated_at")
    private Timestamp updatedAt;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("user_id")
    private UserDtos worker;

    @SerializedName("price")
    private double price;

    @SerializedName("client_id")
    private ClientDtos client;

    @SerializedName("percent")
    private double percent;

    @SerializedName("part")
    private Integer part;

    @SerializedName("active")
    private boolean enabled;


    public ContractDto(int id, String productName, UserDtos worker, double price, ClientDtos client, double percent, Integer part) {
        this.id = id;
        this.productName = productName;
        this.worker = worker;
        this.price = price;
        this.client = client;
        this.percent = percent;
        this.part = part;
    }

    public ContractDto() {
    }


    public boolean isEnable() {
        return enabled;
    }

    public void setEnable(boolean enabled) {
        this.enabled = enabled;
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

    public UserDtos getWorker() {
        return worker;
    }

    public void setWorker(UserDtos worker) {
        this.worker = worker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ClientDtos getClient() {
        return client;
    }

    public void setClient(ClientDtos client) {
        this.client = client;
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
