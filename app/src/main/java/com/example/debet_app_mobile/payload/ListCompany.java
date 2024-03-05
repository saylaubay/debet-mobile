package com.example.debet_app_mobile.payload;

public class ListCompany {

    private int id;
    private String name;

    public ListCompany(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ListCompany() {
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
}
