package com.simpower.model.map;

import com.simpower.model.Model;
import com.simpower.model.time.Clock;

public class Map extends Model {
    private double seed;
    private Resource[] resources;
    private Clock clock;
    private Slot[] slots;
    private int citizens = 0;

    public int getCitizens() {
        return 0;
    }
}
