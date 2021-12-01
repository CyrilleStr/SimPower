package com.simpower.models.map.buildings;

import com.simpower.models.Happiness;

public class House extends Buildings {
    private int level = 1;
    private int citizens = 1;
    private Happiness happiness;

    public int getCitizens() {
        return this.citizens;
    }

    public int getLevel() {
        return this.level;
    }

}
