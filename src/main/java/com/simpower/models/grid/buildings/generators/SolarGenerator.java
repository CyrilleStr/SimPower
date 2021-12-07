package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.Generator;

public class SolarGenerator extends Generator {
    public SolarGenerator() {
        super(20, false, "Solar pannel", 20, 1, 0, 1500);
    }
}
