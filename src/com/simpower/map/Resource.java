package com.simpower.map;

public class Resource {
    private String name = "Unknown";
    private boolean infinite = false;
    private int radius = 1;
    private double quantity = 1;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
