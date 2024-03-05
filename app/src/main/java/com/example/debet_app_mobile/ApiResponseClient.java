package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.ClientDto;
import com.example.debet_app_mobile.payload.ClientDtos;
import com.example.debet_app_mobile.payload.UserDtos;

import java.util.List;

public class ApiResponseClient {

    private String message;

    private boolean success;

    private List<ClientDtos> data;

    public ApiResponseClient(String message, boolean success, List<ClientDtos> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseClient(String message, boolean success) {
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
