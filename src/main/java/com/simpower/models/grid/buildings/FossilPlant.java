package com.simpower.models.grid.buildings;

public abstract class FossilPlant extends ProducerEnergyBuilding {

    private int resourceConsumption;
    private int pollutionRadius;
    public FossilPlant(int servicingCost, int buildingCost, int production, int resourceConsumption, int pollutionRadius) {
        super(servicingCost,buildingCost,production);
        setResourceConsumption(resourceConsumption);
        setPollutionRadius(pollutionRadius);
    }

    public void setResourceConsumption(int consumptionC){
        this.resourceConsumption = consumptionC;
    }

    public void setPollutionRadius(int polluR){
        this.pollutionRadius = polluR;
    }

    @Override
    public int consumeResource() {
        return this.resourceConsumption;
    }

    @Override
    public int generatePollution(){
        return this.pollutionRadius;
    }
}
