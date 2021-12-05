package com.simpower.models.map.buildings.generators;

import com.simpower.models.map.buildings.FossileGenerator;

public class GasGenerator extends FossileGenerator {
    public GasGenerator(){
        super(12, 1250, true, "Gas Plant", 300, 1, 6, 18000);
    }
}
