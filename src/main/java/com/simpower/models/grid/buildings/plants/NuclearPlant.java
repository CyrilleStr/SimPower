package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.GridInfos;

public class NuclearPlant extends FossilPlant {
    public NuclearPlant() {
        super(200,30000, 300, 10, 0, true, GridInfos.resourceStock.URANIUM);
    }
}
