package com.simpower.models.grid.buildings;

public class Building {
    private int servicingCost;
    private  int level;
    private  int buildingCost;

    public Building(int servicingCost, int buildingCost){
        setServicingCost(servicingCost);
        setBuildingCost(buildingCost);
        setLevel(0);
    }

    public void destroyBuilding() {
        // TODO destroy building
    }

    public int getServicingCost(){
        return this.servicingCost;
    }

    public void setServicingCost(int servicingCost_p){
        this.servicingCost = servicingCost_p;
    }

    public int getLevel(){
        return this.level;
    }

    public void setLevel(int level_p){
        this.level = level_p;
    }

    public int getBuildingCost(){
        return this.buildingCost;
    }

    public void setBuildingCost(int buildingCost_p){
        this.buildingCost = buildingCost_p;
    }

    public int collectMoneyIncomes(){
        return 0;
    }

    public int collectMoneyOutcomes(){
        return 0;
    }

    public int produceElectricity(){
        return 0;
    }

    public int consumeElectricity(){
        return 0;
    }

    public int collectResource(){
        return 0;
    }

    public int consumeResource(){
        return 0;
    }

    public int generatePollution(){
        return 0;
    }
}
