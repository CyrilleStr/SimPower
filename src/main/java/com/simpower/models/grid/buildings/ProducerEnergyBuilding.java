package com.simpower.models.grid.buildings;

public abstract class ProducerEnergyBuilding extends Building {

    private int production;

    public ProducerEnergyBuilding(int servicingCost, int buildingCost, int production, boolean isFossil, resourceStock resourceStock){
        super(servicingCost,buildingCost,isFossil, false, resourceStock,false, true);
        setProduction(production);
        setMine(false);
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

    /**
     * Set the energy production of the building
     *
     * @param production_p int
     */
    void setProduction(int production_p){
        this.production = production_p;
    }

    /**
     * Get the energy production of the building
     *
     * @return int
     */
    int getProduction(){
        return this.production;
    }
}
