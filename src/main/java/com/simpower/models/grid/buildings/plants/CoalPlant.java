package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.FossilPlant;

public class CoalPlant extends FossilPlant {
    public CoalPlant() {
        super(40, 4000, 80, 300, 4, true, GridInfos.resourceStock.COAL);
    }
}
