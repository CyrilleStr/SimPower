package com.simpower.models.time;

import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Clock extends Thread {
    private double time; //  1 unit time = 1 Minute = 1 for loop run
    private LocalDateTime dateTime;
    private boolean infiniteDay = true;
    private int speeds[] = new int[]{1,4,7,10,100};
    private int speedIndex = 0;
    private int speed;
    private boolean ticking;
    private Season season;
    private Grid grid;

    // hours when the sun rise / moon rise
    private int sunrise[] = {8,6,8,10};
    private int moonrise[] = {20,21,20,18};

    /*
     * Seasons swap days:
     * 355 == 21st December
     * 80 == 21st March
     * 172 == 21st June
     * 264 == 21st September
     */
    private int solstice[] = {80,172,264,355}; // day of the year

    private enum Season {
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER
    }

    @FXML private Label clockLabel;

    /*
     * Test if a given value is inside a given array
     */
    private boolean contains(int[] arr, int x) {
        for (int a : arr) if (x == a) return true;
        return false;
    }

    public Clock(Grid grid, Label clockLabel_p){
        this.time = 0;
        this.speed = 1;
        this.dateTime = LocalDateTime.now();
        this.clockLabel = clockLabel_p;
        this.ticking = false;

        this.grid = grid;

        if (this.dateTime.getDayOfYear() >= this.solstice[3]) this.setSeason(Season.WINTER);
        else if (this.dateTime.getDayOfYear() >= this.solstice[2]) this.setSeason(Season.AUTUMN);
        else if (this.dateTime.getDayOfYear() >= this.solstice[1]) this.setSeason(Season.SUMMER);
        else if (this.dateTime.getDayOfYear() >= this.solstice[0])  this.setSeason(Season.SPRING);
        else this.setSeason(Season.WINTER);

        // if user start a game after moonrise, start at night
        if (
            this.getDateTime().getHour() <= this.sunrise[this.getSeason().ordinal()] ||
            this.getDateTime().getHour() >= this.moonrise[this.getSeason().ordinal()]
        ) this.switchLight();
    }

    public void run() {
        try {
            this.setTicking(true);
            while (true) {
                sleep(100 / this.getSpeed());
                this.time++;
                this.setDateTime(this.getDateTime().plusMinutes(1));

                Platform.runLater(() -> {
                    // seasons checker
                    if (this.getDateTime().getHour() == 0 && this.getDateTime().getMinute() == 0 && this.contains(this.solstice, this.getDateTime().getDayOfYear()))
                        this.nextSeason();

                    // day/night switch
                    if (this.getDateTime().getHour() == sunrise[this.getSeason().ordinal()] || this.getDateTime().getHour() == moonrise[this.getSeason().ordinal()])
                        this.switchLight();

                    // change label text
                    this.clockLabel.setText(this.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/YYYY")) + (this.isDay() ? " DAY " : " NIGHT ") + this.getSeason());
                });
            }
        } catch (InterruptedException e){
            this.setTicking(false);
        }

    }

    /**
     * Switch between day & night cycle
     */
    private void switchLight() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(this.isDay() ? 0 : -.5);
        this.grid.getGridcontainer().setEffect(colorAdjust);
    }

    /**
     * Change current season for the next one
     */
    private void nextSeason() {
        this.setSeason(Season.values()[(this.getSeason().ordinal() + 1) % Season.values().length]);

        switch (this.getSeason()) {
            case WINTER:
            case SPRING:
                for (Cell[] tmp : this.grid.getCells()) for (Cell cell : tmp) {
                    switch(cell.getCurrentTopLayer()) {
                        case GRASS:
                            cell.setCurrentTopLayer(GridInfos.topLayer.SNOW);
                            break;
                        case RIVER:
                            cell.setCurrentTopLayer(GridInfos.topLayer.ICE);
                            break;
                        case SNOW:
                            cell.setCurrentTopLayer(GridInfos.topLayer.GRASS);
                            break;
                        case ICE:
                            cell.setCurrentTopLayer(GridInfos.topLayer.RIVER);
                            break;
                    }
                }
                this.grid.refreshLayers();
                break;
        }
    }

    /**
     * get the actual season
     * @return Season
     */
    public Season getSeason() { return this.season; }

    /**
     * set actual season to the given value
     * @param s Season
     */
    public void setSeason(Season s) { this.season = s; }

    /**
     * Small function to tell if it's day or night
     * @return boolean true if it's day time
     */
    public boolean isDay() {
        return this.sunrise[getSeason().ordinal()] <= this.dateTime.getHour() && this.dateTime.getHour() < this.moonrise[getSeason().ordinal()] || this.isInfiniteDay();
    }

    /**
     * get the actual date time
     * @return
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * set the date time to the given value
     * @param t
     */
    public void setDateTime(LocalDateTime t) {
        this.dateTime = t;
    }

    /**
     * set infinite day on if true is given
     * @param b boolean
     */
    public void setInfiniteDay(boolean b) {
        this.infiniteDay = b;
    }

    /**
     * tell if the game is running in infinite day or not
     * @return current state
     */
    public boolean isInfiniteDay() {
        return this.infiniteDay;
    }

    /**
     * Set clock to the next available speed
     */
    public void nextSpeed() {
        this.speedIndex = (this.speedIndex + 1) % this.speeds.length;
        this.setSpeed(this.speeds[this.speedIndex]);
    }

    /**
     * set clock speed
     * @param speed_p speed for the clock
     */
    public void setSpeed(int speed_p) {
        this.speed = speed_p <= 100 && speed_p > 0 ? speed_p : 1;
    }

    /**
     * get the actual speed of the clock
     * @return current speed;
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * get the clock ticking state
     * @return true if the clock is running
     */
    public boolean isTicking() {
        return ticking;
    }

    /**
     * set clock ticking state
     * @param ticking true if the clock is running
     */
    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public int getDayCount() {
        return this.dateTime.getDayOfYear();
    }
}
