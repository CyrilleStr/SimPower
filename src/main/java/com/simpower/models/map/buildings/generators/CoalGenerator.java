package com.simpower.models.map.buildings.generators;

import com.simpower.models.map.buildings.FossileGenerator;

public class CoalGenerator extends FossileGenerator {
    public CoalGenerator() {
        super(10, 1000, true, "Coal Plant", 250, 1, 8, 15000);
    }
}
