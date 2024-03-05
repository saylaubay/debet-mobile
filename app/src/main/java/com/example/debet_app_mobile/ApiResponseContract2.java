package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.BuyDto;
import com.example.debet_app_mobile.payload.BuyDtos;
import com.example.debet_app_mobile.payload.BuyDtoss;

import java.util.List;

public class ApiResponseContract2 {

    private String message;

    private boolean success;

    private BuyDtoss data;

    public ApiResponseContract2(String message, boolean success, BuyDtoss data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseContract2(String message, boolean success) {
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


    public BuyDtoss getData() {
        return data;
    }

    public void setData(BuyDtoss data) {
        this.data = data;
    }
}
