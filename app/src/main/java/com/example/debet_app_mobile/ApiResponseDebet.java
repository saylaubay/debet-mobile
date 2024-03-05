package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.DebetDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseDebet {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<DebetDto> data;

    public ApiResponseDebet(String message, boolean success, List<DebetDto> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseDebet() {
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

    public List<DebetDto> getData() {
        return data;
    }

    public void setData(List<DebetDto> data) {
        this.data = data;
    }
}
