package com.example.debet_app_mobile;

import com.google.gson.annotations.SerializedName;

public class TestDto {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    @SerializedName("active")
    private boolean active;
//    private boolean active;

    public TestDto(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public TestDto() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
