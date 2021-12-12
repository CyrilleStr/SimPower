package com.simpower.models.grid;

import com.simpower.models.grid.buildings.Buildings;

public class Cell implements GridInfos {
    private int pos_x = 0;
    private int pos_y = 0;
    private buildingLayer currentBuildingLayer;
    private resourceLayer currentResourceLayer;
    private topLayer currentTopLayer;
    private pollutionLayer currentPollutionLayer;

    public Cell(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
        currentBuildingLayer = buildingLayer.NONE;
        currentResourceLayer = resourceLayer.NONE;
        currentTopLayer = topLayer.NONE;
        currentPollutionLayer = pollutionLayer.NONE;
    }

    /* Getters and Setters */
    public int getPos_x() { return this.pos_x; }
    public int getPos_y() { return this.pos_y; }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }
    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public resourceLayer getCurrentResourceLayer(){ return this.currentResourceLayer; }
    public topLayer getCurrentTopLayer(){ return this.currentTopLayer; }
    public pollutionLayer getCurrentPollutionLayer(){ return this.currentPollutionLayer; }
    public buildingLayer getCurrentBuildingLayer() { return currentBuildingLayer; }

    public void setCurrentResourceLayer(resourceLayer currentResourceLayer) {
        this.currentResourceLayer = currentResourceLayer;
    }
    public void setCurrentTopLayer(topLayer currentTopLayer) {
        this.currentTopLayer = currentTopLayer;
    }
    public void setCurrentPollutionLayer(pollutionLayer currentPollutionLayer) {
        this.currentPollutionLayer = currentPollutionLayer;
    }
    public void setCurrentBuildingLayer(buildingLayer currentBuildingLayer) {
        this.currentBuildingLayer = currentBuildingLayer;
    }

    public boolean isResourceLayerEmpty() {
        if (this.currentResourceLayer == resourceLayer.NONE) return true;
        return false;
    }
    public boolean isTopLayerEmpty() {
        if (this.currentTopLayer == topLayer.NONE) return true;
        return false;
    }
    public boolean isPollutionLayerEmpty() {
        if (this.currentPollutionLayer == pollutionLayer.NONE) return true;
        return false;
    }
    public boolean isBuildingEmpty() {
        if (this.currentBuildingLayer == buildingLayer.NONE) return true;
        return false;
    }
}
