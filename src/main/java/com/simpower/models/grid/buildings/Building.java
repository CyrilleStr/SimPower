package com.simpower.models.grid.buildings;

import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.Cell;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class Building implements GridInfos {
    private int servicingCost;
    private int level;
    private int buildingCost;
    private boolean active;
    private boolean isFossil;
    private boolean isRoad;
    private boolean isHouse;
    private boolean isEnergyProducer;
    private resourceStock resourceStockEnum;

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
     * @return the resources quantity to be added or removed
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

    public GridInfos.resourceStock getResourceStockEnum() {
        return resourceStockEnum;
    }

    public void setResourceStockEnum(GridInfos.resourceStock resourceStockEnum) {
        this.resourceStockEnum = resourceStockEnum;
    }

    public void setServicingCost(int servicingCost_p){
        this.servicingCost = servicingCost_p;
    }

    public int getLevel(){
        return this.level;
    }

    public void setLevel(int level_p){
        this.level = level_p;
    }

    public int getBuildingCost(){
        return this.buildingCost;
    }

    public void setBuildingCost(int buildingCost_p){
        this.buildingCost = buildingCost_p;
    }

    public int generatePollution(){
        return 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {this.active = active;}

    public void setFossil(boolean fossil) {
        isFossil = fossil;
    }
    
    public boolean isFossil() {
        return isFossil;
    }

    public void setRoad(boolean road) {
        isRoad = road;
    }

    public boolean isRoad() {
        return isRoad;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public boolean isEnergyProducer() {
        return isEnergyProducer;
    }

    public void setEnergyProducer(boolean energyProducer) {
        isEnergyProducer = energyProducer;
    }

    public int updateHappiness(){
        System.out.println("not a house \n");
        return 0;
    }
}
