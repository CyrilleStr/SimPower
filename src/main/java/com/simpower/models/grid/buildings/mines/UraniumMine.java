package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.buildings.Mine;

public class UraniumMine extends Mine {
    public UraniumMine() {
        super(16, 18, 1);
        setResourceProduction(3);
    }
}
