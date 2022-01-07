package com.simpower.models.grid.buildings;

import com.simpower.models.grid.GridInfos;

public abstract class Building implements GridInfos {
    private int servicingCost;
    private int level;
    private int buildingCost;
    private boolean active;
    private boolean isFossil;
    private boolean isRoad;
    private boolean isHouse;
    private boolean isEnergyProducer;
    private boolean isMine;
    private resourceStock resourceStockEnum;
    private boolean cellPolluted;

    public Building(int servicingCost, int buildingCost, boolean isFossil, boolean isRoad, resourceStock resourceStock, boolean isHouse, boolean isEnergyProducer){
        setServicingCost(servicingCost);
        setBuildingCost(buildingCost);
        setFossil(isFossil);
        setRoad(isRoad);
        setHouse(isHouse);
        setEnergyProducer(isEnergyProducer);
        setResourceStockEnum(resourceStock);
        setLevel(0);
        setActive(true);
    }

    /**
     * Allows you to destroy a building in-game
     *
     */
    public void destroyBuilding() {
        // TODO destroy building
    }

    /**
     * Return the building cost to be removed from the money amount
     *
     * @return the cost as a negative number
     */
    public int changeMoneyAmount(){
        return -this.servicingCost;
    }

    /**
     * The resource stock changes operated by the building (add or remove)
     * 
     * @return the resource quantity to be added or removed
     */
    public int resourceStockChange(){
        return 0;
    }

    /**
     * The electricity stock changes operated by the building (add or remove)
     *
     * @return the electricity quantity to be added or removed
     */
    public int electricityStockChange(){
        return 0;
    }
    
    /* Getters and setters */

    /**
     * Getter of the resource stock
     *
     * @return enum
     */
    public GridInfos.resourceStock getResourceStockEnum() {
        return this.resourceStockEnum;
    }

    /**
     * Setter of the resource stock
     *
     * @param resourceStockEnum_p enum to set
     */
    public void setResourceStockEnum(GridInfos.resourceStock resourceStockEnum_p) {
        this.resourceStockEnum = resourceStockEnum_p;
    }

    /**
     * Setter of the servicing cost
     *
     * @param servicingCost_p int to set
     */
    public void setServicingCost(int servicingCost_p){
        this.servicingCost = servicingCost_p;
    }

    /**
     * Getter of the level of the building
     *
     * @return int
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * Setter of the level of the building
     *
     * @param level_p int to set
     */
    public void setLevel(int level_p){
        this.level = level_p;
    }

    /**
     * Getter of the building cost
     *
     * @return int
     */
    public int getBuildingCost(){
        return this.buildingCost;
    }

    /**
     * Setter of the building cost
     *
     * @param buildingCost_p int to set
     */
    public void setBuildingCost(int buildingCost_p){
        this.buildingCost = buildingCost_p;
    }

    /**
     * Used to simulate the pollution generation in its environment
     *
     * @return int
     */
    public int getPollution(){
        return 0;
    }

    /**
     * Return if the building is active or not
     *
     * @return boolean
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Set the active parameter of a building
     *
     * @param active_p boolean to set
     */
    public void setActive(boolean active_p) {
        this.active = active_p;
    }

    /**
     * Set if the building uses fossile ressources or not
     *
     * @param fossil_p boolean to set
     */
    public void setFossil(boolean fossil_p) {
        this.isFossil = fossil_p;
    }

    /**
     * Return if the building is using fossile resources or not
     *
     * @return boolean
     */
    public boolean isFossil() {
        return this.isFossil;
    }

    /**
     * Set if the building is a road or not
     *
     * @param road_p boolean to set
     */
    public void setRoad(boolean road_p) {
        this.isRoad = road_p;
    }

    /**
     * Return if the building is a road
     *
     * @return boolean
     */
    public boolean isRoad() {
        return this.isRoad;
    }

    /**
     * Set if the building is a house or not
     *
     * @param house_p boolean to set
     */
    public void setHouse(boolean house_p) {
        this.isHouse = house_p;
    }

    /**
     * Return if the building is a house
     *
     * @return boolean
     */
    public boolean isHouse() {
        return this.isHouse;
    }

    /**
     * Return if the building is producing energy or not
     *
     * @return boolean
     */
    public boolean isEnergyProducer() {
        return this.isEnergyProducer;
    }

    /**
     * Set if the building is producing energy or not
     *
     * @param energyProducer_p boolean to set
     */
    public void setEnergyProducer(boolean energyProducer_p) {
        this.isEnergyProducer = energyProducer_p;
    }

    /**
     * Sets the pollution state of the cell
     * @param cellPolluted
     */
    public void setCellPolluted(boolean cellPolluted) {
        this.cellPolluted = cellPolluted;
    }

    /**
     * Returns the pollution state of the cell
     * @return boolean
     */
    public boolean isCellPolluted() {
        return cellPolluted;
    }

    /**
     * Updates the happiness of a house relative to the pollution and electricity provided. Also updates its money income.
     * @param electricityProvided
     * @return int
     */
    public int updateHappiness(float electricityProvided){
        return 0;
    }

    /**
     * Returns true if the building id a mine
     * @return
     */
    public boolean isMine() {
        return isMine;
    }

    /**
     * Sets a building to a mine
     * @param mine
     */
    public void setMine(boolean mine) {
        isMine = mine;
    }
}
