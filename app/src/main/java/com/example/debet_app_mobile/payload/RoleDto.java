package com.example.debet_app_mobile.payload;

import java.util.List;

public class RoleDto {

    private List<Role> data;

    public RoleDto(List<Role> data) {
        this.data = data;
    }


    public List<Role> getData() {
        return data;
    }

    public void setData(List<Role> data) {
        this.data = data;
    }
}
