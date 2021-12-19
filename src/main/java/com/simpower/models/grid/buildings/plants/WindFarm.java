package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class WindFarm extends ProducerEnergyBuilding {
    public WindFarm() {
        super(50,10000,100, false, GridInfos.resourceStock.NONE);
    }
}
