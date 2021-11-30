package com.simpower.model.map.resources;

import com.simpower.model.map.Resource;

public class Wind extends Resource {
    private float direction = 90; // rotation in degree 0 is facing North;

    public Wind() {
        this.setInfinite(true);
        this.setRadius(100);
        this.setName("Wind");
        this.setQuantity(1);
    }
}
