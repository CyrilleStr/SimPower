package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.GridInfos;

public class OilPlant extends FossilPlant {
    public OilPlant() {
        super(100, 5000, 120, 100, 3, true, GridInfos.resourceStock.OIL);
    }
}
