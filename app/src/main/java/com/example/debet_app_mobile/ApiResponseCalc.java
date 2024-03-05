package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.CalcRes;

public class ApiResponseCalc {

    private String message;

    private boolean success;

    private CalcRes data;

    public ApiResponseCalc(String message, boolean success, CalcRes data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseCalc(String message, boolean success) {
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

    public CalcRes getData() {
        return data;
    }

    public void setData(CalcRes data) {
        this.data = data;
    }
}
