package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class SolarGenerator extends ProducerEnergyBuilding {
    public SolarGenerator(int servicingCost, int pollutionRadius, int production) {
        super(servicingCost,pollutionRadius,production);
    }

}
