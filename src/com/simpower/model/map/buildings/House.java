package com.simpower.model.map.buildings;

public class House extends Buildings {
    private int level = 1;
    private int citizens = 1;
    private com.simpower.model.Happiness happiness;

    public int getCitizens() {
        return this.citizens;
    }

    public int getLevel() {
        return this.level;
    }

}
