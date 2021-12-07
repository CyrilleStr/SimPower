package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.Generator;

public class WaterGenerator extends Generator {
    public WaterGenerator() {
        super(40, true, "Water wheel", 35, 1, 0, 4000);
    }
}
