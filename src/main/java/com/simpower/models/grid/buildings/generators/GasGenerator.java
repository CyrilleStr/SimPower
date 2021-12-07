package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.FossileGenerator;

public class GasGenerator extends FossileGenerator {
    public GasGenerator(){
        super(12, 1250, true, "Gas Plant", 300, 1, 6, 18000);
    }
}
