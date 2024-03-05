package com.example.debet_app_mobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Company {
    private String message;

    protected boolean success;
    private List<TestDto> data;

    public Company(List<TestDto> data) {
        this.data = data;
    }

    public List<TestDto> getData() {
        return data;
    }

    public void setData(List<TestDto> data) {
        this.data = data;
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
}
