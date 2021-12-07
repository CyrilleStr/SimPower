package com.simpower.models.grid.buildings;

import com.simpower.models.Happiness;

public class House extends Buildings {
    private int level = 1;
    private int citizens = 1;
    private Happiness happiness;

    public House(){
        super("House");
        happiness.setHappinessLevel(100);
        level = 1;
        citizens = 1;
    }

    public void setLevel(int levelL){
        this.level = levelL;
    }

    public void setCitizens(int citizensC){
        this.citizens = citizensC;
    }

    public int getCitizens() {
        return this.citizens;
    }

    public int getLevel() {
        return this.level;
    }

}
