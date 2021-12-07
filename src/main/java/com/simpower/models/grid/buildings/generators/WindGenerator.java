package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.Generator;

public class WindGenerator extends Generator {
    public WindGenerator() {
        super(25, true, "Wind turbine", 15, 1, 0, 2500);
    }
}
