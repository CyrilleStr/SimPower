package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class SolarPlant extends ProducerEnergyBuilding {
    public SolarPlant() {
        super(50,10500,110, false, GridInfos.resourceStock.NONE);
    }

}
