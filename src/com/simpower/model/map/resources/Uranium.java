package com.simpower.model.map.resources;

import com.simpower.model.map.Resource;

import java.util.Random;

public class Uranium extends Resource {
    private Random rand = new Random();

    public Uranium() {
        this.setInfinite(false);
        this.setName("Uranium");
        this.setRadius(rand.nextInt(100));
        this.setQuantity(rand.nextInt(100));
    }
}
