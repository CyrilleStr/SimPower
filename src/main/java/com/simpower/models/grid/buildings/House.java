package com.simpower.models.grid.buildings;

import com.simpower.models.grid.GridInfos;

public class House extends ConsumerEnergyBuilding {
    private int inhabitant;
    private int inhabitantCapacity;
    private int happiness;
    private int moneyIncome;

    public House(){
        super(0, 2000, 400, false, GridInfos.resourceStock.NONE, true);
        setHappiness(100);
        setInhabitant(1);
        setInhabitantCapacity(5);
        setMoneyIncome(200);
    }

    /* Getters and setters */

    /**
     * Return the money earned by taxes
     *
     * @return the money as a positive number
     */
    @Override
    public int changeMoneyAmount(){
        return this.moneyIncome;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setInhabitant(int inhabitant) {
        this.inhabitant = inhabitant;
    }

    public int getInhabitant() {
        return inhabitant;
    }

    public int getInhabitantCapacity() {
        return inhabitantCapacity;
    }

    public void setInhabitantCapacity(int inhabitantCapacity) {
        this.inhabitantCapacity = inhabitantCapacity;
    }

    public void setMoneyIncome(int moneyIncome) {
        this.moneyIncome = moneyIncome;
    }

}
