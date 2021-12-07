package com.simpower.models.grid.buildings;

public class Buildings {
    private String name = "Unknown";

    public Buildings(String nameN){
        setName(nameN);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void destroyBuilding() {
    }
}
