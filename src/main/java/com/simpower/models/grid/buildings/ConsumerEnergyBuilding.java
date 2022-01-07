package com.simpower.models.grid.buildings;

public abstract class ConsumerEnergyBuilding extends Building {
    protected int electricityNeeded;

    public ConsumerEnergyBuilding(int servicingCost, int buildingCost,int consumeEnergy, boolean isFossil, resourceStock resourceStock, boolean isHouse, boolean isMine){
        super(servicingCost,buildingCost,isFossil, false, resourceStock, isHouse, false);
        setElectricityNeeded(consumeEnergy);
        setMine(isMine);
    }

    /**
     * Return the electricity quantity to be removed from the stock
     *
     * @return the electricity quantity as a negative number
     */
    @Override
    public int electricityStockChange(){ return -this.electricityNeeded; }

    /**
     * Set the energy needed by the building
     *
     * @param electricityNeeded_p energy needed to set
     */
    public void setElectricityNeeded(int electricityNeeded_p) {
        this.electricityNeeded = electricityNeeded_p;
    }
}
