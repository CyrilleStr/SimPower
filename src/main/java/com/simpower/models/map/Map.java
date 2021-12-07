package com.simpower.models.map;

import com.simpower.models.time.Clock;

public class Map {
    private double seed;
    private ResourceAvailable availableResource = new ResourceAvailable();
    private final Clock clock = new Clock();
    private Slot[] slots;
    private int citizens = 0;

    public Map(){}

    public void setSeed(double seedS){
        this.seed = seedS;
    }

    public int getCitizens() {
        return this.citizens;
    }
}
