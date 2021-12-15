package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class SolarPlant extends ProducerEnergyBuilding {
    public SolarPlant(int servicingCost, int pollutionRadius, int production) {
        super(servicingCost,pollutionRadius,production);
    }

}
