package com.simpower.models.grid.resources;

import com.simpower.models.grid.Resource;

import java.util.Random;

public class Oil extends Resource {
    private Random rand = new Random();

    public Oil() {
        this.setInfinite(false);
        this.setName("Oil");
        this.setRadius(rand.nextInt(100));
        this.setQuantity(rand.nextInt(100));
    }
}
