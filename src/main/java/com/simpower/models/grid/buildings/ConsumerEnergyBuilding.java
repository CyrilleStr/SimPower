package com.simpower.models.grid.buildings;

public class ConsumerEnergyBuilding extends Building {
    protected int consumeEnergy;

    public ConsumerEnergyBuilding(int servicingCost, int buildingCost,int consumeEnergy){
        super(servicingCost,buildingCost);
        setConsumeEnergy(consumeEnergy);
    }

    public void setConsumeEnergy(int consumeEnergy) {
        this.consumeEnergy = consumeEnergy;
    }

    public int getConsumeEnergy() {
        return consumeEnergy;
    }
}
