package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class WindGenerator extends ProducerEnergyBuilding {
    public WindGenerator(int servicingCost, int pollutionRadius, int production) {
        super(servicingCost,pollutionRadius,production);
    }
}
