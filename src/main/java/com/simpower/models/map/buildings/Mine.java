package com.simpower.models.map.buildings;

public class Mine extends WorkingBuilding {
    private float productionRate;

    public Mine(float prodP, String NameN, int serviceS, int levelL, int polluR, int buildingC) {
        super(NameN, serviceS, levelL, polluR, buildingC);
        setProductionRate(prodP);
    }

    public void setProductionRate(float prodP){
        this.productionRate = prodP;
    }
}
