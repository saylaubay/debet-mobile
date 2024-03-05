package com.example.debet_app_mobile.payload;

public class CalcDto {

    private double summa;

    private int part;

    private double percent;

    public CalcDto(double summa, int part, double percent) {
        this.summa = summa;
        this.part = part;
        this.percent = percent;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
