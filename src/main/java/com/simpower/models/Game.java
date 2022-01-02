package com.simpower.models;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.*;
import com.simpower.models.time.Clock;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Game implements GridInfos{
    private Clock clock;
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;
    private int money;
    private int globalHappiness;
    private int electricityStock;
    private int coalStock;
    private int gasStock;
    private int oilStock;
    private int uraniumStock;
    private Map <resourceStock, Function<Integer, Integer>> resourceStockToStockMap = new HashMap<>();

    public Game (Grid grid, Clock clock) {
        this.grid = grid;
        this.clock = clock;
        this.createdAt = LocalDateTime.now();
        setMoney(100000);
        setGlobalHappiness(100);
        setElectricityStock(10);
        setCoalStock(0);
        setGasStock(0);
        setOilStock(0);
        setUraniumStock(0);
        this.loadData();
    }

    /**
     * Call every cell operations to be done each day such as collectResource or collectMoney
     */
    public void eachDay() {
        boolean active;
        int tmpHappiness = 0;
        int houseCount = 0;
        for (Cell[] cellX : this.grid.getCells()) {
            for (Cell cell : cellX) {
                active = true;
                Building building = cell.getCurrentBuilding();
                // Check if there's a building and if it isn't a road
                if (building != null && !building.isRoad()) {

                    // Check if the building can have all what it needs to be still active
                    if (building.isHouse()) { // The building is a house;
                        if (-building.electricityStockChange() >= electricityStock) { // Not enough electricity for the house
                            active = false;
                        }
                        tmpHappiness += building.updateHappiness();
                        houseCount += 1;
                    } else {
                        if (building.isFossil())
                            if (building.isEnergyProducer()) // The building is a fossil plant
                                if (-building.resourceStockChange() >= resourceStockToStockMap.get(building.getResourceStockEnum()).apply(0))
                                    // The fossil plant has not enough resources to work
                                    active = false;

                        if (-building.changeMoneyAmount() >= this.money)
                            // The building has servicing cost greater than the money amount
                            active = false;
                    }

                    // Building is active => daily operation can be done
                    if (active) {
                        building.setActive(true);
                        if (building.isFossil()) {
                            resourceStockToStockMap.get(building.getResourceStockEnum()).apply(building.resourceStockChange());
                        }
                        this.electricityStock += building.electricityStockChange();
                        this.money += building.changeMoneyAmount();

                    } else {
                        building.setActive(false);
                    }
                }
            }
        }
        System.out.println("housecount :" + houseCount +"\n");
        System.out.println("tmphappiness :" + tmpHappiness +"\n");

        if(houseCount >0)
            globalHappiness = tmpHappiness / houseCount;
        System.out.println("global happiness : "+globalHappiness+"\n");
        // todo: add automatic save
    };

    /**
     * Load map converting enum stock to Integer stock
     */
    public void loadData(){
        resourceStockToStockMap.put(resourceStock.GAS, tmp -> {
            return this.gasStock += tmp;
        });
        resourceStockToStockMap.put(resourceStock.OIL, tmp -> {
            return this.oilStock += tmp;
        });
        resourceStockToStockMap.put(resourceStock.COAL, tmp -> {
            return this.coalStock += tmp;
        });
        resourceStockToStockMap.put(resourceStock.URANIUM, tmp -> {
            return this.uraniumStock += tmp;
        });
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setElectricityStock(int electricityStock) {
        this.electricityStock = electricityStock;
    }

    public int getElectricityStock() {
        return electricityStock;
    }

    public void setCoalStock(int coalStock) {
        this.coalStock = coalStock;
    }

    public int getCoalStock() {
        return coalStock;
    }

    public void setGasStock(int gasStock) {
        this.gasStock = gasStock;
    }

    public int getGasStock() {
        return gasStock;
    }

    public void setOilStock(int oilStock) {
        this.oilStock = oilStock;
    }

    public int getOilStock() {
        return oilStock;
    }

    public void setUraniumStock(int uraniumStock) {
        this.uraniumStock = uraniumStock;
    }

    public int getUraniumStock() {
        return uraniumStock;
    }

    public void setGlobalHappiness(int globalHappiness){this.globalHappiness = globalHappiness;}

    public int getGlobalhappiness(){return globalHappiness;}
}
