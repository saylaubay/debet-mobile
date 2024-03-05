package com.example.debet_app_mobile;

import java.util.List;

public class Company2 {
    private String message;

    protected boolean success;
    private List<TestDto2> data;

    public Company2(List<TestDto2> data) {
        this.data = data;
    }

    public List<TestDto2> getData() {
        return data;
    }

    public void setData(List<TestDto2> data) {
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
