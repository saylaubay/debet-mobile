package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.UserDto;
import com.example.debet_app_mobile.payload.UserDtos;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseUser {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<UserDtos> data;

    public ApiResponseUser(String message, boolean success, List<UserDtos> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseUser(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public void setData(List<UserDtos> data) {
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

    public List<UserDtos> getData() {
        return data;
    }
}
