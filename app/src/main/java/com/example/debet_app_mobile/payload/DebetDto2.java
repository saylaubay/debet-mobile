package com.example.debet_app_mobile.payload;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class DebetDto2 {

//    private int nomer;

    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private Timestamp createdAt;

    @SerializedName("updated_at")
    private Timestamp updatedAt;

    @SerializedName("month_name")
    private String monthName;

    @SerializedName("summa")
    private double summa;

    @SerializedName("contract_id")
    private ContractDto contract;

    @SerializedName("paid")
    private boolean paid;

    @SerializedName("pay_date")
    private Timestamp payDate;


    public DebetDto2(String monthName, double summa, ContractDto contract, boolean paid) {
        this.monthName = monthName;
        this.summa = summa;
        this.contract = contract;
        this.paid = paid;
    }

    public DebetDto2(String monthName, double summa, ContractDto contract) {
        this.monthName = monthName;
        this.summa = summa;
        this.contract = contract;
    }

    public DebetDto2() {
    }

//    public int getNomer() {
//        return nomer;
//    }
//
//    public void setNomer(int nomer) {
//        this.nomer = nomer;
//    }

    public Timestamp getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        this.payDate = payDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public ContractDto getContract() {
        return contract;
    }

    public void setContract(ContractDto contract) {
        this.contract = contract;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
