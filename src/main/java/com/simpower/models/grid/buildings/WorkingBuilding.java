package com.simpower.models.grid.buildings;

public class WorkingBuilding extends Buildings {
    private int servicingCost;
    private int level;
    private int pollutionRadius;
    private int buildingCost;

    public WorkingBuilding(String NameN, int serviceS, int levelL, int polluR, int buildingC){
        super(NameN);
        setServicingCost(serviceS);
        setLevel(levelL);
        setPollutionRadius(polluR);
        setBuildingCost(buildingC);
    }

    public void upgradeBuilding() {
    }

    public void setServicingCost(int serviceS){
        this.servicingCost = serviceS;
    }

    public void setLevel(int levelL){
        this.level = levelL;
    }

    public void setPollutionRadius(int polluR){
        this.pollutionRadius = polluR;
    }

    public void setBuildingCost(int buildingC){
        this.buildingCost = buildingC;
    }

    public int getLevel() {
        return this.level;
    }

    public int getPollutionRadius() {
        return this.pollutionRadius;
    }
}
