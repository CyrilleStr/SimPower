package com.simpower.models.grid.buildings;

public abstract class ConsumerEnergyBuilding extends Building {
    protected int electricityNeeded;

    public ConsumerEnergyBuilding(int servicingCost, int buildingCost,int consumeEnergy, boolean isFossil, resourceStock resourceStock, boolean isHouse){
        super(servicingCost,buildingCost,isFossil, false, resourceStock, isHouse, false);
        setElectricityNeeded(consumeEnergy);
    }

    /**
     * Return the electricity quantity to be removed from the stock
     *
     * @return the electricity quantity as a negative number
     */
    @Override
    public int electricityStockChange(){
        return -electricityNeeded;
    }

    public void setElectricityNeeded(int electricityNeeded) {
        this.electricityNeeded = electricityNeeded;
    }
}
