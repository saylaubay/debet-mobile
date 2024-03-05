package com.example.debet_app_mobile;

public class ApiResponse {

    private String message;

    private boolean success;

    private ResDto data;

    public ApiResponse(String message, boolean success, ResDto data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(String message, boolean success) {
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

    public ResDto getData() {
        return data;
    }

    public void setData(ResDto data) {
        this.data = data;
    }
}
