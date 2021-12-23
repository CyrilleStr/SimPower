package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.GridInfos;

public class UraniumMine extends Mine {
    public UraniumMine() {
        super(16, 18, 1, GridInfos.resourceStock.URANIUM);
        setResourceProduction(3);
    }
}
