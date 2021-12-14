package com.simpower.models.grid.buildings;

public class FossileGenerator extends ProducerEnergyBuilding {

    protected int resourceConsumption;
    protected int pollutionRadius;

    public FossileGenerator(int servicingCost, int buildingCost, int production, int resourceConsumption, int pollutionRadius) {
        super(servicingCost,buildingCost,production);
        setResourceConsumption(resourceConsumption);
        setPollutionRadius(pollutionRadius);
    }

    public void setResourceConsumption(int consumptionC){
        this.resourceConsumption = consumptionC;
    }

    public int getResourceConsumption() {
        return resourceConsumption;
    }

    public void setPollutionRadius(int polluR){
        this.pollutionRadius = polluR;
    }

    public int getPollutionRadius() {
        return pollutionRadius;
    }
}
