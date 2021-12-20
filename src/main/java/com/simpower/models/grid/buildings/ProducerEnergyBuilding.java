package com.simpower.models.grid.buildings;

import com.simpower.models.grid.GridInfos;

public abstract class ProducerEnergyBuilding extends Building {

    private int production;

    public ProducerEnergyBuilding(int servicingCost, int buildingCost, int production, boolean isFossil, resourceStock resourceStock){
        super(servicingCost,buildingCost,isFossil, false, resourceStock,false, true);
        setProduction(production);
    }

    /**
     * Return the electricity quantity to be added to the stock
     *
     * @return the electricity quantity as a positive number
     */
    @Override
    public int electricityStockChange(){
        return this.production;
    }

    /* Getters and setters */

    void setProduction(int production_p){
        this.production = production_p;
    }

    int getProduction(){
        return this.production;
    }
}
