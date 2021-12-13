package com.simpower.models;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.simpower.models.grid.Grid;
import com.simpower.models.time.Clock;
import javafx.application.Platform;

public class Game extends Thread {
    private Clock clock;
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;

    private LocalDateTime localDate;

    public Game (Grid grid, Clock clock) {
        this.grid = grid;
        this.clock = clock;
        this.createdAt = LocalDateTime.now();

        this.localDate = LocalDateTime.now();
    }

    public void run() {
        try {
            while (true) {
                sleep(1);
                Platform.runLater(() -> {
                    // detect each ms if the saved local day date is different from the clock one
                    // if so we actualise it and runs methods that need to be run each day
                    if (this.clock.getDateTime().getDayOfYear() != this.getCurrentDay()) this.eachDay();

                });
            }
        } catch (InterruptedException e) {}
    }

    /**
     * Add given numbers of day to the current date
     * @param days
     */
    private void addDaysToCurrentDate(int days) { this.localDate.plusDays(days); }

    /**
     * Get the actual saved day
     * @return
     */
    private int getCurrentDay() { return this.localDate.getDayOfYear(); }

    /**
     * Call operation to be done each day
     */
    private void eachDay() {
        this.addDaysToCurrentDate(1);

        // todo: add automatic save

        // todo: add money grabbing system (les impôts & dépences tmtc)
    };
}
