package com.simpower.models.grid.buildings;

public abstract class ProducerEnergyBuilding extends Building {

    private int production;

    public ProducerEnergyBuilding(int servicingCost, int buildingCost, int production){
        super(servicingCost,buildingCost);
        setProduction(production);
    }

    void setProduction(int production_p){
        this.production = production_p;
    }

    int getProduction(){
        return this.production;
    }

    @Override
    public int produceElectricity(){
        return this.production;
    }
}
