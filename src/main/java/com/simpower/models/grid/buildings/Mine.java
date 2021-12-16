package com.simpower.models.grid.buildings;

public abstract class Mine extends ConsumerEnergyBuilding {
    private int resourceProduction;

    public Mine(int servicingCost, int buildingCost,int consumeEnergy) {
        super(servicingCost, buildingCost, consumeEnergy);
    }

    public void setResourceProduction(int resourceProduction) {
        this.resourceProduction = resourceProduction;
    }

    @Override
    public int collectResource(){
        return resourceProduction;
    }
}
