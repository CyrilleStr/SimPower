package com.simpower.models.time;

import java.util.Date;

public class Clock {
    private double time;
    private boolean ticking = true;
    private int speed = 1; // time speed, goes faster if greater
    private Date date;
    private final Seasons season = Seasons.SPRING;
    private final boolean day = true; // false if it's night time

    public void setSpeed(int speedS) {
        this.speed = speedS;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date dateD) {
        this.date = dateD;
    }

    public String getDateToString() {
        return null;
    }

    public double getTime() {
        return this.time;
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public void tickFaster() {
    }

    public void tickSlower() {
    }
}
