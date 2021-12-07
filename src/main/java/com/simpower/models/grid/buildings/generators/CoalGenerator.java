package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.FossileGenerator;

public class CoalGenerator extends FossileGenerator {
    public CoalGenerator() {
        super(10, 1000, true, "Coal Plant", 250, 1, 8, 15000);
    }
}
