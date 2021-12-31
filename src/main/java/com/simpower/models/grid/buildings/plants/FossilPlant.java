package com.simpower.models.grid.buildings.plants;

import com.simpower.models.grid.buildings.ProducerEnergyBuilding;

public abstract class FossilPlant extends ProducerEnergyBuilding {

    private int resourceConsumption;
    private int pollutionRadius;

    public FossilPlant(int servicingCost, int buildingCost, int production, int resourceConsumption, int pollutionRadius, boolean isFossil, resourceStock resourceStock) {
        super(servicingCost,buildingCost,production,isFossil, resourceStock);
        setResourceConsumption(resourceConsumption);
        setPollutionRadius(pollutionRadius);
    }

    /**
     * Return the resources to be taken from the resource stock
     *
     * @return the resources as a negative number
     */
    @Override
    public int resourceStockChange(){
        return -this.resourceConsumption;
    }

    /* Getters and setters */

    /**
     * Used to generate pollution based on the pollution radius of the plant
     *
     * @return
     */
    @Override
    public int generatePollution(){
        //TODO la pollution à générer mais comme dans Building.java
        return this.pollutionRadius;
    }

    /**
     * Set the ressource consumption of the plant
     *
     * @param resourceConsumption_p int to set
     */
    public void setResourceConsumption(int resourceConsumption_p){
        this.resourceConsumption = resourceConsumption_p;
    }

    /**
     * Set the pollution radius of the plant
     *
     * @param pollutionRadius_p int to set
     */
    public void setPollutionRadius(int pollutionRadius_p){
        this.pollutionRadius = pollutionRadius_p;
    }
}
