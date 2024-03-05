package com.example.debet_app_mobile.payload;

import com.google.gson.annotations.SerializedName;

public class Role {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String roleName;

    private String authority;

    public Role(int id, String roleName, String authority) {
        this.id = id;
        this.roleName = roleName;
        this.authority = authority;
    }

    public Role() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
