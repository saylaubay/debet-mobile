package com.example.debet_app_mobile.payload;

public class ListClient {

    private int id;
    private String firstName;
    private String phone;

    public ListClient(int id, String firstName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.phone = phone;
    }

    public ListClient() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
