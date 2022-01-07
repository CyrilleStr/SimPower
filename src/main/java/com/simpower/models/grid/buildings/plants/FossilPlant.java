package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public abstract class FossilPlant extends ProducerEnergyBuilding {

    private int resourceConsumption;

    public FossilPlant(int servicingCost, int buildingCost, int production, int resourceConsumption, int pollutionRadius, boolean isFossil, resourceStock resourceStock) {
        super(servicingCost,buildingCost,production,isFossil, resourceStock);
        setResourceConsumption(resourceConsumption);
    }

    /* Getters and setters */

    /**
     * Return the resources to be taken from the resource stock
     *
     * @return the resources as a negative number
     */
    @Override
    public int resourceStockChange(){
        return -this.resourceConsumption;
    }

    /**
     * Set the ressource consumption of the plant
     *
     * @param resourceConsumption_p int to set
     */
    public void setResourceConsumption(int resourceConsumption_p){
        this.resourceConsumption = resourceConsumption_p;
    }

}
