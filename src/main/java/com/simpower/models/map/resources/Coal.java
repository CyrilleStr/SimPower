package com.simpower.models.map.resources;

import com.simpower.models.map.Resource;

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