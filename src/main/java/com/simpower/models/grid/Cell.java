package com.simpower.models.grid;

import com.simpower.models.grid.buildings.Building;

public class Cell implements GridInfos {
    private int pos_x = -1;
    private int pos_y = -1;
    private boolean polluted = false;
    private int pollutionAge = 0;
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

    /**
     * Get the x position of the cell in the grid
     *
     * @return int
     */
    public int getPos_x() {
        return this.pos_x;
    }

    /**
     * Get the y position of the cell in the grid
     *
     * @return int
     */
    public int getPos_y() {
        return this.pos_y;
    }

    /**
     * Set the x position of the cell in the grid
     *
     * @param pos_x_p int to set
     */
    public void setPos_x(int pos_x_p) {
        this.pos_x = pos_x_p;
    }

    /**
     * Set the y position of the cell in the grid
     *
     * @param pos_y_p int to set
     */
    public void setPos_y(int pos_y_p) {
        this.pos_y = pos_y_p;
    }

    /**
     * Get the current resource layer of the cell
     *
     * @return resourceLayer
     */
    public resourceLayer getCurrentResourceLayer(){ return this.currentResourceLayer; }

    /**
     * Get the current top layer of the cell
     *
     * @return topLayer
     */
    public topLayer getCurrentTopLayer(){ return this.currentTopLayer; }

    /**
     * Get the current pollution layer of the cell
     *
     * @return pollutionLayer
     */
    public pollutionLayer getCurrentPollutionLayer(){ return this.currentPollutionLayer; }

    /**
     * Get the current building layer of the cell
     *
     * @return buildingLayer
     */
    public buildingLayer getCurrentBuildingLayer() { return currentBuildingLayer; }

    /**
     * Set the current resource layer of the cell
     *
     * @param currentResourceLayer_p resourceLayer to set
     */
    public void setCurrentResourceLayer(resourceLayer currentResourceLayer_p) {
        this.currentResourceLayer = currentResourceLayer_p;
    }

    /**
     * Set the current top layer of the cell
     *
     * @param currentTopLayer_p topLayer to set
     */
    public void setCurrentTopLayer(topLayer currentTopLayer_p) {
        this.currentTopLayer = currentTopLayer_p;
    }

    /**
     * Set the current pollution layer of the cell
     *
     * @param currentPollutionLayer_p pollutionLayer to set
     */
    public void setCurrentPollutionLayer(pollutionLayer currentPollutionLayer_p) {
        this.currentPollutionLayer = currentPollutionLayer_p;
    }

    /**
     * Set the current building layer of the cell
     *
     * @param currentBuildingLayer_p buildingLayer to set
     */
    public void setCurrentBuildingLayer(buildingLayer currentBuildingLayer_p) {
        this.currentBuildingLayer = currentBuildingLayer_p;
    }

    /**
     * Test if the resource layer is empty or not
     *
     * @return boolean
     */
    public boolean isResourceLayerEmpty() {
        return this.currentResourceLayer == resourceLayer.NONE;
    }

    /**
     * Test if the top layer is empty or not
     *
     * @return boolean
     */
    public boolean isTopLayerEmpty() {
        return this.currentTopLayer == topLayer.NONE;
    }

    /**
     * Test if the pollution layer is empty or not
     *
     * @return boolean
     */
    public boolean isPollutionLayerEmpty() {
        return this.currentPollutionLayer == pollutionLayer.NONE;
    }

    /**
     * Test if the building layer is empty or not
     *
     * @return boolean
     */
    public boolean isBuildingEmpty() {
        return this.currentBuilding == null;
    }

    /**
     * Set the building in the cell
     *
     * @param currentBuilding_p Building to set
     */
    public void setCurrentBuilding(Building currentBuilding_p) {
        this.currentBuilding = currentBuilding_p;
    }

    /**
     * Get the building in the cell
     *
     * @return Building
     */
    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public boolean isPolluted() {
        return polluted;
    }

    public void setPolluted(boolean polluted) {
        this.polluted = polluted;
    }

    public void setPollutionAge(int pollutionAge) {
        this.pollutionAge = pollutionAge;
    }

    public int getPollutionAge() {
        return pollutionAge;
    }
}
