package com.simpower.models.grid.buildings.generators;

import com.simpower.models.grid.buildings.FossileGenerator;

public class OilGenerator extends FossileGenerator {
    public OilGenerator() {
        super(14, 1500, true, "Oil Plant", 325, 1, 5, 16500);
    }
}
