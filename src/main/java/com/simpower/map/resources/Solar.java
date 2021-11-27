package com.simpower.map.resources;

import com.simpower.map.Resource;

public class Solar extends Resource {
    public Solar() {
        this.setInfinite(true);
        this.setName("Solar");
        this.setRadius(100);
        this.setQuantity(100);
    }
}
