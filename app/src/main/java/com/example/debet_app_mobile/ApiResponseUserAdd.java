package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.UserDtos;

import java.util.List;

public class ApiResponseUserAdd {

    private String message;

    private boolean success;

    private UserDtos data;

    public ApiResponseUserAdd(String message, boolean success, UserDtos data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseUserAdd(String message, boolean success) {
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

    public UserDtos getData() {
        return data;
    }

    public void setData(UserDtos data) {
        this.data = data;
    }
}
