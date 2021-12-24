package com.simpower.models.grid;

import com.simpower.models.grid.buildings.Building;
import com.simpower.models.grid.buildings.plants.CoalPlant;

public class Cell implements GridInfos {
    private int pos_x = -1;
    private int pos_y = -1;
    private buildingLayer currentBuildingLayer;
    private resourceLayer currentResourceLayer;
    private topLayer currentTopLayer;
    private pollutionLayer currentPollutionLayer;
    private Building currentBuilding;

    public Cell() {
        currentBuildingLayer = buildingLayer.NONE;
        currentResourceLayer = resourceLayer.NONE;
        currentTopLayer = topLayer.NONE;
        currentPollutionLayer = pollutionLayer.NONE;
        currentBuilding = null;
    }

    public Cell(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
        currentBuildingLayer = buildingLayer.NONE;
        currentResourceLayer = resourceLayer.NONE;
        currentTopLayer = topLayer.NONE;
    }

    /* Getters and Setters */
    public int getPos_x() { return this.pos_x; }
    public int getPos_y() { return this.pos_y; }

    public void setPos_x(int pos_x_p) {
        this.pos_x = pos_x_p;
    }
    public void setPos_y(int pos_y_p) {
        this.pos_y = pos_y_p;
    }

    public resourceLayer getCurrentResourceLayer(){ return this.currentResourceLayer; }

    public topLayer getCurrentTopLayer(){ return this.currentTopLayer; }

    public pollutionLayer getCurrentPollutionLayer(){ return this.currentPollutionLayer; }

    public buildingLayer getCurrentBuildingLayer() { return currentBuildingLayer; }

    public void setCurrentResourceLayer(resourceLayer currentResourceLayer_p) {
        this.currentResourceLayer = currentResourceLayer_p;
    }

    public void setCurrentTopLayer(topLayer currentTopLayer_p) {
        this.currentTopLayer = currentTopLayer_p;
    }

    public void setCurrentPollutionLayer(pollutionLayer currentPollutionLayer_p) {
        this.currentPollutionLayer = currentPollutionLayer_p;
    }

    public void setCurrentBuildingLayer(buildingLayer currentBuildingLayer_p) {
        this.currentBuildingLayer = currentBuildingLayer_p;
    }

    public boolean isResourceLayerEmpty() {
        return this.currentResourceLayer == resourceLayer.NONE;
    }

    public boolean isTopLayerEmpty() {
        return this.currentTopLayer == topLayer.NONE;
    }

    public boolean isPollutionLayerEmpty() {
        return this.currentPollutionLayer == pollutionLayer.NONE;
    }

    public boolean isBuildingEmpty() {
        return this.currentBuilding == null;
    }

    public void setCurrentBuilding(Building currentBuilding_p) {
        this.currentBuilding = currentBuilding_p;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }
}
