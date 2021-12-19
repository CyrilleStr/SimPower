package com.simpower.models.grid.buildings;

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
        return -resourceConsumption;
    }

    /* Getters and settesr */

    @Override
    public int generatePollution(){
        return this.pollutionRadius;
    }

    public void setResourceConsumption(int consumptionC){
        this.resourceConsumption = consumptionC;
    }

    public void setPollutionRadius(int polluR){
        this.pollutionRadius = polluR;
    }
}
