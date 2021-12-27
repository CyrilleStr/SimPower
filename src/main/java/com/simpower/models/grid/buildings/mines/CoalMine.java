package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.GridInfos;

public class CoalMine extends Mine {
    public CoalMine() {
        super(100, 3000, 30, GridInfos.resourceStock.COAL);
        setResourceProduction(50);
    }
}
