package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.CalcRes;

import java.util.List;

public class ApiResponseCompany {

    private String message;

    private boolean success;

    private List<TestDto> data;

    public ApiResponseCompany(String message, boolean success, List<TestDto> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseCompany(String message, boolean success) {
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

    public List<TestDto> getData() {
        return data;
    }

    public void setData(List<TestDto> data) {
        this.data = data;
    }
}
