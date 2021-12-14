package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.FossileGenerator;

public class CoalGenerator extends FossileGenerator {
    public CoalGenerator() {
        super(150, 6000, 100, 150, 250);
    }
}
