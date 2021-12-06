package com.simpower.models.map;

import com.simpower.models.map.buildings.Buildings;

public class Slot {
    private int pos_x = 0;
    private int pos_y = 0;
    private boolean empty = true;
    private Buildings building = null;
    private final int pollutionLevel = 0;
    private boolean River = false;

    public int getPos_x() {
        return this.pos_x;
    }

    public void setPos_x(int xTemp){this.pos_x = xTemp;}

    public int getPos_y() {
        return this.pos_y;
    }

    public void setPos_y(int yTemp){this.pos_y = yTemp;}

    public int getPollutionLevel() {
        return this.pollutionLevel;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setBuilding(Buildings building) {
        if (this.isEmpty()) this.empty = false;

        this.building = building;
    }

    public boolean isRiver(){
        return this.River;
    }

    public void setRiver(boolean riverR){
        this.River = riverR;
    }
}
