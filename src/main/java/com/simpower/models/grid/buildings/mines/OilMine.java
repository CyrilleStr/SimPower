package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.buildings.Mine;

public class OilMine extends Mine {
    public OilMine() {
        super(50, 2000, 80);
        setResourceProduction(50);
    }
}
