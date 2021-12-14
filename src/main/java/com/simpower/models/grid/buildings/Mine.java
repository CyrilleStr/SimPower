package com.simpower.models.grid.buildings;

public class Mine extends ConsumerEnergyBuilding {
    private float productionRate;

    public Mine(int servicingCost, int buildingCost,int consumeEnergy) {
        super(servicingCost, buildingCost, consumeEnergy);
        setProductionRate(1);
    }

    public void setProductionRate(float productionRate) {
        this.productionRate = productionRate;
    }

    public float getProductionRate() {
        return productionRate;
    }
}
