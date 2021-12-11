package com.simpower.models.time;

import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Seasons swap days:
 * 355 == 21st December
 * 80 == 21st March
 * 172 == 21st June
 * 264 == 21st September
 */

public class Clock extends Thread implements TimeInfos {
    private double time; //  1 unit time = 1 Minute = 1 for loop run
    private LocalDateTime dateTime;
    private boolean infiniteDay = false;
    private int speed;
    private boolean ticking;
    private Season season;
    private GridPane gridContainer;
    private Cell cells[][];
    @FXML private Label clockLabel;

    public Clock(GridPane gridContainer, Cell cells[][], Label clockLabel_p){
        this.time = 0;
        this.speed = 1;
        this.dateTime = LocalDateTime.now();
        this.clockLabel = clockLabel_p;
        this.ticking = false;

        this.gridContainer = gridContainer;
        this.cells = cells;

        if (this.dateTime.getDayOfYear() >= solstice[3]) this.season = Season.WINTER;
        else if (this.dateTime.getDayOfYear() >= solstice[2]) this.season = Season.AUTUMN;
        else if (this.dateTime.getDayOfYear() >= solstice[1]) this.season = Season.SUMMER;
        else if (this.dateTime.getDayOfYear() >= solstice[0])  this.season = Season.SPRING;
        else this.season = Season.WINTER;
    }

    public Clock(GridPane gridContainer, Cell cells[][], LocalDateTime savedDateTime, double savedTime){
        this.time = savedTime;
        this.dateTime = savedDateTime;
        this.gridContainer = gridContainer;
    }


    public void run(){
        try {
            this.ticking = true;
            while (true) {
                sleep(100/this.speed);
                this.time++;
                this.dateTime = this.dateTime.plusMinutes(1);

                Platform.runLater(() -> {
                    // seasons checker
                    if (this.dateTime.getHour() == 0 && this.dateTime.getMinute() == 0 && this.contains(this.solstice, this.dateTime.getDayOfYear()))
                        this.nextSeason();

                    // day/night switch
                    if (this.dateTime.getHour() == sunrise[this.getSeason().ordinal()] || this.dateTime.getHour() == moonrise[this.getSeason().ordinal()])
                        this.switchLight();

                    // change label text
                    this.clockLabel.setText(this.dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/YYYY")) + (this.isDay() ? " DAY " : " NIGHT ") + this.getSeason());
                });
            }
        } catch (InterruptedException e){
            this.ticking = false;
        }

    }

    private void switchLight() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(isDay() ? 0 : -.5);
        gridContainer.setEffect(colorAdjust);
    }

    public Season getSeason() { return this.season; }
    public void setSeason(Season s) { this.season = s; }

    /**
     * Change current season for the next one
     */
    private void nextSeason() {
        this.setSeason(Season.values()[(this.getSeason().ordinal() + 1) % Season.values().length - 1]);

        if (this.getSeason() == Season.WINTER) {
           for (Cell[] tmp : cells) for (Cell cell : tmp) {
               switch(cell.getCurrentTopLayer()) {
                   case GRASS:
                       cell.setCurrentTopLayer(GridInfos.topLayer.SNOW);
                       break;
                   case RIVER:
                       cell.setCurrentTopLayer(GridInfos.topLayer.ICE);
                       break;
               }
           }
        }
    }

    /**
     * Small function to tell if it's day or night
     * @return boolean true if it's day time
     */
    public boolean isDay() {
        return this.sunrise[getSeason().ordinal()] <= this.dateTime.getHour() && this.dateTime.getHour() < this.moonrise[getSeason().ordinal()] || this.infiniteDay;
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
