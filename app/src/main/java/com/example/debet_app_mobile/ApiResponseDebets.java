package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.DebetDto;

import java.util.List;

public class ApiResponseDebets {

    private String message;

    private boolean success;

    private DebetDto data;

    public ApiResponseDebets(String message, boolean success, DebetDto data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseDebets() {
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

    public DebetDto getData() {
        return data;
    }

    public void setData(DebetDto data) {
        this.data = data;
    }
}
