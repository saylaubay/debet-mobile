package com.example.debet_app_mobile.payload;

import com.example.debet_app_mobile.CompanyDto;
import com.example.debet_app_mobile.TestDto;
import com.google.gson.annotations.SerializedName;

public class ClientDto {

    @SerializedName("id")
    private int id;

    @SerializedName("first_ame")
    private String first_ame;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("company_id")
    private CompanyDto company_id;

    public ClientDto(String first_ame, String last_name, String phone, CompanyDto company_id) {
        this.first_ame = first_ame;
        this.last_name = last_name;
        this.phone = phone;
        this.company_id = company_id;
    }

    public ClientDto(String first_ame, String last_name, String phone) {
        this.first_ame = first_ame;
        this.last_name = last_name;
        this.phone = phone;
    }

    public ClientDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_ame;
    }

    public void setFirstName(String firstName) {
        this.first_ame = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CompanyDto getCompanyId() {
        return company_id;
    }

    public void setCompanyId(CompanyDto company_id) {
        this.company_id = company_id;
    }
}
