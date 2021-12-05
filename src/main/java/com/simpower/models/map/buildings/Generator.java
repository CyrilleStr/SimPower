package com.simpower.models.map.buildings;

public class Generator extends WorkingBuilding {
    private int production;
    private boolean activeAtNight;

    public Generator(int prodP, boolean activeN, String NameN, int serviceS, int levelL, int polluR, int buildingC) {
        super(NameN, serviceS, levelL, polluR, buildingC);
        this.production = prodP;
        this.activeAtNight = activeN;
    }

    public void setProduction(int prodP){
        this.production = prodP;
    }

    public int getProduction(){
        return this.production;
    }

    public void setActiveAtNight(boolean activeN){
        this.activeAtNight = activeN;
    }

    public boolean getActiveAtNight(){
        return this.activeAtNight;
    }
}