package com.example.debet_app_mobile.payload;

import com.example.debet_app_mobile.TestDto;
import com.google.gson.annotations.SerializedName;

public class ClientDtos {

    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("company_id")
    private TestDto company;

    public ClientDtos(String firstName, String lastName, String phone, TestDto company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.company = company;
    }

    public ClientDtos(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public ClientDtos() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TestDto getCompany() {
        return company;
    }

    public void setCompany(TestDto company) {
        this.company = company;
    }
}
