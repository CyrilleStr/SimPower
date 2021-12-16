package com.simpower.models.grid.buildings;

public abstract class ConsumerEnergyBuilding extends Building {
    protected int electricityNeeded;

    public ConsumerEnergyBuilding(int servicingCost, int buildingCost,int consumeEnergy){
        super(servicingCost,buildingCost);
        setElectricityNeeded(consumeEnergy);
    }

    public void setElectricityNeeded(int electricityNeeded) {
        this.electricityNeeded = electricityNeeded;
    }

    @Override
    public int consumeElectricity(){
        return electricityNeeded;
    }
}
