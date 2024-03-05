package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.ClientDto;
import com.example.debet_app_mobile.payload.ClientDtos;

import java.util.List;

public class ApiResponseClient2 {

    private String message;

    private boolean success;

    private List<ClientDtos> data;

    public ApiResponseClient2(String message, boolean success, List<ClientDtos> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseClient2(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public List<ClientDtos> getData() {
        return data;
    }

    public void setData(List<ClientDtos> data) {
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
