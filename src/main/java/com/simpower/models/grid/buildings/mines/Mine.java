package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.buildings.ConsumerEnergyBuilding;

public abstract class Mine extends ConsumerEnergyBuilding {
    private int resourceProduction;

    public Mine(int servicingCost, int buildingCost,int consumeEnergy, resourceStock resourceStock) {
        super(servicingCost, buildingCost, consumeEnergy, true, resourceStock, false);
    }

    /**
     * Return the resources to be added to the resource stock
     *
     * @return the resources as a positive number
     */
    @Override
    public int resourceStockChange(){
        return this.resourceProduction;
    }

    /* Getters and setters */

    /**
     * Set the resource production of the mine
     *
     * @param resourceProduction int
     */
    public void setResourceProduction(int resourceProduction) {
        this.resourceProduction = resourceProduction;
    }

}
