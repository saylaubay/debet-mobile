package com.example.debet_app_mobile.payload;

public class ListContract {

    private int id;
    private String clientName;
    private String productName;
    private double summa;

    public ListContract(int id, String clientName, String productName, double summa) {
        this.id = id;
        this.clientName = clientName;
        this.productName = productName;
        this.summa = summa;
    }

    public ListContract(String clientName, String productName, double summa) {
        this.clientName = clientName;
        this.productName = productName;
        this.summa = summa;
    }



    public ListContract() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }
}
