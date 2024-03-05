package com.example.debet_app_mobile;

public class ApiRes {

    private String message;

    private boolean success;


    public ApiRes(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiRes() {
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
