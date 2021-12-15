package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.buildings.FossilePlant;

public class CoalPlant extends FossilePlant {
    public CoalPlant() {
        super(150, 6000, 100, 150, 250);
    }

    @Override
    public int collectResource(){
        return 5;
    }

    @Override
    public int consumeResource(){
        return 5;
    }

    @Override
    public int produceElectricity(){
        return 3;
    }
}
