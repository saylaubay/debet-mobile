package com.example.debet_app_mobile.payload;

import com.google.gson.annotations.SerializedName;

public class CalcRes {

    @SerializedName("part")
    private int part;

    @SerializedName("allPercentSumma")
    private double allPercentSumma;

    @SerializedName("monthSumma")
    private double monthSumma;

    @SerializedName("allSumma")
    private double allSumma;

    @SerializedName("monthPercentSumma")
    private double monthPercentSumma;


    public CalcRes(int part, double allPercentSumma, double monthSumma, double allSumma, double monthPercentSumma) {
        this.part = part;
        this.allPercentSumma = allPercentSumma;
        this.monthSumma = monthSumma;
        this.allSumma = allSumma;
        this.monthPercentSumma = monthPercentSumma;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public double getAllPercentSumma() {
        return allPercentSumma;
    }

    public void setAllPercentSumma(double allPercentSumma) {
        this.allPercentSumma = allPercentSumma;
    }

    public double getMonthSumma() {
        return monthSumma;
    }

    public void setMonthSumma(double monthSumma) {
        this.monthSumma = monthSumma;
    }

    public double getAllSumma() {
        return allSumma;
    }

    public void setAllSumma(double allSumma) {
        this.allSumma = allSumma;
    }

    public double getMonthPercentSumma() {
        return monthPercentSumma;
    }

    public void setMonthPercentSumma(double monthPercentSumma) {
        this.monthPercentSumma = monthPercentSumma;
    }
}
