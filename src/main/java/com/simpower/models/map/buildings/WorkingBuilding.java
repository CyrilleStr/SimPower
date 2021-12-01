package com.simpower.models.map.buildings;

public class WorkingBuilding extends Buildings {
    private int servicingCost;
    private int level;
    private int pollutionRadius;
    private int buildingCost;

    public void upgradeBuilding() {
    }

    public int getLevel() {
        return this.level;
    }

    public int getPollutionRadius() {
        return this.pollutionRadius;
    }
}
