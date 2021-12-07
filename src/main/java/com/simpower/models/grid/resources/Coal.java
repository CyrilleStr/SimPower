package com.simpower.models.grid.resources;

import com.simpower.models.grid.Resource;

import java.util.Random;

public class Coal extends Resource {
    private Random rand = new Random();

    public Coal() {
        this.setInfinite(false);
        this.setName("Coal");
        this.setRadius(rand.nextInt(100));
        this.setQuantity(rand.nextInt(100));
    }

}
