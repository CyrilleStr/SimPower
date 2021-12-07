package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.FossileGenerator;

public class UraniumGenerator extends FossileGenerator {
    public UraniumGenerator() {
        super(8,5000, true, "Nuclear Plant", 1000, 1, 0, 20000);
    }
}
