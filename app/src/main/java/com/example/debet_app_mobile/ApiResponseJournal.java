package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.DebetDto;

import java.util.List;

public class ApiResponseJournal {

    private String message;

    private boolean success;

    private List<DebetDto> data;

    public ApiResponseJournal() {
    }

    public ApiResponseJournal(String message, boolean success, List<DebetDto> data) {
        this.message = message;
        this.success = success;
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

    public List<DebetDto> getData() {
        return data;
    }

    public void setData(List<DebetDto> data) {
        this.data = data;
    }
}
