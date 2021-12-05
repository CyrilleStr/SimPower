package com.simpower.models.map.buildings;

public class FossileGenerator extends Generator {
    private int consumption;

    public FossileGenerator(int consumptionC, int prodP, boolean activeN, String NameN, int serviceS, int levelL, int polluR, int buildingC) {
        super(prodP, activeN, NameN, serviceS, levelL, polluR, buildingC);
        setConsumption(consumptionC);
    }

    public void setConsumption(int consumptionC){
        this.consumption = consumptionC;
    }

    public void upgradeConsumption() {
    }
}
