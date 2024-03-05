package com.example.debet_app_mobile;

public class ApiResponse2 {

    private String message;

    private boolean success;

    private String data;

    public ApiResponse2(String message, boolean success, String data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse2(String message, boolean success) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
