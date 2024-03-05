package com.example.debet_app_mobile;

public class CompDto {

    private int id;

    private String name;
    
    private boolean active;

    public CompDto(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public CompDto(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public CompDto(String name) {
        this.name = name;
    }

    public CompDto() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
