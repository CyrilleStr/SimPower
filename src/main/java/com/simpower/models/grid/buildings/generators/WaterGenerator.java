package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class WaterGenerator extends ProducerEnergyBuilding {
    public WaterGenerator(int servicingCost, int pollutionRadius, int production) {
        super(servicingCost,pollutionRadius,production);
    }
}
