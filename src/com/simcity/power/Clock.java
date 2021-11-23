package com.simcity.power;

public class Clock {
    private double time;
    private boolean ticking = true;
    private int speed;
    private double date;
    private Seasons season = Seasons.SPRING;
    private boolean day = true;

    public void setSpeed(int speed) {
    }

    public void getSpeed() {
    }

    public void setDate(double date) {
    }

    public double getDate() {
        return 0;
    }

    public String getDateToString() {
        return null;
    }

    public double getTime() {
        return this.time;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public boolean isTicking() {
        return false;
    }

    public void tickFaster() {
    }

    public void tickSlower() {
    }
}
