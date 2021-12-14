package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.FossileGenerator;

public class GasGenerator extends FossileGenerator {
    public GasGenerator(){
        super(12, 1250, 150, 300, 1);
    }
}
