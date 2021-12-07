package com.simpower.models.map;

import com.simpower.controllers.GameController;
import com.simpower.models.map.buildings.Buildings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Slot implements MapInfos{
    private int pos_x = 0;
    private int pos_y = 0;
    private boolean empty = true;
    private Buildings building = null;
    private resourceLayer currentResourceLayer;
    private topLayer currentTopLayer;
    private pollutionLayer currentPollutionLayer;

    public Slot(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
        currentResourceLayer = resourceLayer.NONE;
        currentTopLayer = topLayer.NONE;
        currentPollutionLayer = pollutionLayer.NONE;
    }

    public int getPos_x() {
        return this.pos_x;
    }

    public int getPos_y() {
        return this.pos_y;
    }

    public resourceLayer getCurrentResourceLayer(){ return this.currentResourceLayer; }

    public topLayer getCurrentTopLayer(){ return this.currentTopLayer; }

    public pollutionLayer getCurrentPollutionLayer(){ return this.currentPollutionLayer; }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setBuilding(Buildings building) {
        if (this.isEmpty()) this.empty = false;

        this.building = building;
    }
}
