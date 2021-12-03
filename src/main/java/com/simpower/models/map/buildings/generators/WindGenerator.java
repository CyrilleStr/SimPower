package com.simpower.models.map.buildings.generators;

import com.simpower.models.map.buildings.Generator;

public class WindGenerator extends Generator {
    public WindGenerator() {
        super(25, true, "Wind turbine", 15, 1, 0, 2500);
    }
}
