package com.example.debet_app_mobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseHammesi {

    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Hammesi> data;

    public ApiResponseHammesi(String message, boolean success, List<Hammesi> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseHammesi(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Hammesi> getData() {
        return data;
    }

    public void setData(List<Hammesi> data) {
        this.data = data;
    }
}
