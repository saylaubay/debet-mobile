package com.example.debet_app_mobile.payload;

import java.util.List;

public class ApiResponseContractResp {

    private String message;

    private boolean success;

    private List<ContractDto> data;

    public ApiResponseContractResp(String message, boolean success, List<ContractDto> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponseContractResp(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


    public ApiResponseContractResp() {
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

    public List<ContractDto> getData() {
        return data;
    }

    public void setData(List<ContractDto> data) {
        this.data = data;
    }
}
