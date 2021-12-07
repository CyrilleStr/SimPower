package com.simpower.models.map.resources;

import com.simpower.models.map.Resource;

import java.util.Random;

public class Gas extends Resource {
    private Random rand = new Random();

    public Gas() {
        this.setInfinite(false);
        this.setName("Gas");
        this.setRadius(rand.nextInt(100));
        this.setQuantity(rand.nextInt(100));
    }
}
