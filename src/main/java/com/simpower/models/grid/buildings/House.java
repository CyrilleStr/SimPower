package com.simpower.models.grid.buildings;

public class House extends ConsumerEnergyBuilding {
    private int inhabitant;
    private int inhabitantCapacity;
    private int happiness;

    public House(){
        super(100, 100, 100);
        setHappiness(100);
        setInhabitant(1);
        setInhabitantCapacity(5);
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setInhabitant(int inhabitant) {
        this.inhabitant = inhabitant;
    }

    public int getInhabitant() {
        return inhabitant;
    }

    public int getInhabitantCapacity() {
        return inhabitantCapacity;
    }

    public void setInhabitantCapacity(int inhabitantCapacity) {
        this.inhabitantCapacity = inhabitantCapacity;
    }
}
