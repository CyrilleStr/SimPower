package com.simpower.models.grid.resources;

import com.simpower.models.grid.Resource;

public class Solar extends Resource {
    public Solar() {
        this.setInfinite(true);
        this.setName("Solar");
        this.setRadius(100);
        this.setQuantity(100);
    }
}
