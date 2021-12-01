package com.simpower.models.time;

import java.util.Date;

public class Clock {
    private double time;
    private boolean ticking = true;
    private int speed = 1; // time speed, goes faster if greater
    private Date date;
    private final Seasons season = Seasons.SPRING;
    private final boolean day = true; // false if it's night time

    public void setSpeed(int speed) {
    }

    public void getSpeed() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
    }

    public String getDateToString() {
        return null;
    }

    public double getTime() {
        return this.time;
    }

    public boolean isTicking() {
        return false;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public void tickFaster() {
    }

    public void tickSlower() {
    }
}
