package com.simcity.power;

public class Slot {
    private int pos_x = 0;
    private int pos_y = 0;
    private boolean empty = true;
    private Buildings building = null;
    private int pollutionLevel = 0;

    public int getPos_x() {
        return this.pos_x;
    }
    public int getPos_y() {
        return this.pos_y;
    }
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
}
