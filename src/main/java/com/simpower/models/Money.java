package com.simpower.models;

public class Money {
    private int amount = 50000;
    private float taxRate = 10;
    private int totalServicingCost = 0;

    public Money(){}

    public int getAmount() {
        return 0;
    }

    public void setAmount(int amountA) {
        this.amount = amountA;
    }

    public int turnover(int citizens, float taxRate, int totalServicingCost) {
        return 0;
    }

    public void setTaxRate(float taxRateChange) {
        this.taxRate = taxRateChange;
    }

    public float getTaxRate() {
        return this.taxRate;
    }

    public void updateServicingCost(int servicingCost) {

    }
}
