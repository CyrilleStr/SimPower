package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.GridInfos;

public class UraniumMine extends Mine {
    public UraniumMine() {
        super(250, 8000, 60, GridInfos.resourceStock.URANIUM);
        setResourceProduction(45);
    }
}
