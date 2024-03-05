package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.BuyDtos;
import com.example.debet_app_mobile.payload.UserDtos;

import java.util.List;

public class ApiResponseContract {

    private String message;

    private boolean success;

    private List<BuyDtos> data;

    public ApiResponseContract(String message, boolean success, List<BuyDtos> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseContract(String message, boolean success) {
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

    public List<BuyDtos> getData() {
        return data;
    }

    public void setData(List<BuyDtos> data) {
        this.data = data;
    }
}
