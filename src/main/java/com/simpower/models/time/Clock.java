package com.simpower.models.time;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock extends Thread{
    private double time; //  1 unit time = 1 Minute = 1 for loop run
    private LocalDateTime dateTime;
    private int speed;
    private boolean ticking;
    private boolean isDay; // false if it's night time
    @FXML private Label clockLabel;

    public Clock(Label clockLabel_p){
        this.time = 0;
        this.speed = 1;
        this.dateTime = LocalDateTime.now();
        this.clockLabel = clockLabel_p;
        this.ticking = false;
    }

    public Clock(LocalDateTime savedDateTime, double savedTime){
        this.time = savedTime;
        this.dateTime = savedDateTime;
    }

    public void run(){
        try{
            this.ticking = true;
            for(;;){
                sleep(100/this.speed);
                this.time++;
                this.dateTime = this.dateTime.plusMinutes(1);
                Platform.runLater(() -> this.clockLabel.setText(this.dateTime.format(DateTimeFormatter.ofPattern("hh:mm dd/MM/YYYY"))));
            }
        }catch(InterruptedException e){
            System.out.println("Pause");
            this.ticking = false;
        }
    }

    public void setSpeed(int speed_p) {
        this.speed = speed_p;
    }

    public int getSpeed() {
        return this.speed;
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }
}
