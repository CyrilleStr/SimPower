package com.simpower.models.map.resources;

import com.simpower.models.map.Resource;

public class Solar extends Resource {
    public Solar() {
        this.setInfinite(true);
        this.setName("Solar");
        this.setRadius(100);
        this.setQuantity(100);
    }
}
