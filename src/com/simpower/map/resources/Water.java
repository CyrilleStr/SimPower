package com.simpower.map.resources;

import com.simpower.map.Resource;

public class Water extends Resource {
    private float direction = 90; // rotation in degree 0 is facing North;

    public Water() {
        this.setInfinite(true);
        this.setName("Water");
        this.setRadius(100);
        this.setQuantity(100);
    }
}
