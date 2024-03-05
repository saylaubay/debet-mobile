package com.example.debet_app_mobile;

import com.google.gson.annotations.SerializedName;

public class TestDto2 {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    @SerializedName("active")
    private int active;
//    private boolean active;

    public TestDto2(int id, String name, int active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public TestDto2() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
