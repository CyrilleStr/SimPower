package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public class WindFarm extends ProducerEnergyBuilding {
    public WindFarm(int servicingCost, int pollutionRadius, int production) {
        super(servicingCost,pollutionRadius,production);
    }
}
