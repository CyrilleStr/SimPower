package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.FossilPlant;

public class GasPlant extends FossilPlant {
    public GasPlant(){
        super(120, 6000, 140, 90, 2, true, GridInfos.resourceStock.GAS);
    }
}
