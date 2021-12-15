package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class WaterMill extends ProducerEnergyBuilding {
    public WaterMill(int servicingCost, int pollutionRadius, int production) {
        super(servicingCost,pollutionRadius,production);
    }
}
