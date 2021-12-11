package com.simpower.models;

import java.nio.file.Path;
import java.time.LocalDateTime;

import com.simpower.models.grid.Grid;
import com.simpower.models.time.Clock;

public class Game {
    private Clock clock;
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;

    public Game (Grid grid, Clock clock) {
        this.grid = grid;
        this.clock = clock;
        this.createdAt = LocalDateTime.now();
    }
}
