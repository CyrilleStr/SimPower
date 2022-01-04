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
import javafx.scene.image.ImageView;

import static java.lang.Math.abs;

public class Game implements GridInfos{
    private Clock clock;
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;
    private int money;
    private int globalHappiness;
    private int globalPollution;
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

                // Update pollution
                if(cell.isPolluted()) {
                    if(cell.getPollutionAge() > POLLUTION_PERSISTANCE_DAY)
                        // Pollution disappear after a certain number of day without being re-polluted
                        cell.setPolluted(false);
                    else
                        // Increment pollution age
                        cell.setPollutionAge(cell.getPollutionAge() + 1);
                }

                Building building = cell.getCurrentBuilding();
                if (building == null || building.isRoad())
                    continue; // continue loop if the building is a road or null

                if (building.isHouse()) {
                    // set the house as active if there is enough electricty
                    building.setActive(abs(building.electricityStockChange()) < electricityStock);
                    tmpHappiness += building.updateHappiness();
                    houseCount++;
                } else
                    // Set the building as active if there is enough money for the servicing cost
                    building.setActive(abs(building.changeMoneyAmount()) < this.money);

                if (building.isFossil())
                    if (building.isEnergyProducer())
                        // set the resource plan as active if there is enough resources
                        building.setActive(abs(building.resourceStockChange()) < resourceStockToStockMap.get(building.getResourceStockEnum()).apply(0));

                if (building.isActive()) {
                    if (building.isFossil()) {
                        resourceStockToStockMap.get(building.getResourceStockEnum()).apply(building.resourceStockChange());
                        if(building.isEnergyProducer()) // If the building pollute
                            this.grid.generatePollutionAroundCell(cell);
                    }
                    this.electricityStock += building.electricityStockChange();
                    this.money += building.changeMoneyAmount();
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

    /**
     * Set the money
     *
     * @param money int to set
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Get the money
     *
     * @return int
     */
    public int getMoney() {
        return money;
    }

    /**
     * Set the electricity stocked
     *
     * @param electricityStock int to set
     */
    public void setElectricityStock(int electricityStock) {
        this.electricityStock = electricityStock;
    }

    /**
     * Get the electricity stocked
     *
     * @return int
     */
    public int getElectricityStock() {
        return electricityStock;
    }

    /**
     * Set the coal stocked
     *
     * @param coalStock int to set
     */
    public void setCoalStock(int coalStock) {
        this.coalStock = coalStock;
    }

    /**
     * Get the coal stocked
     *
     * @return int
     */
    public int getCoalStock() {
        return coalStock;
    }

    /**
     * Set the gas stocked
     *
     * @param gasStock int to set
     */
    public void setGasStock(int gasStock) {
        this.gasStock = gasStock;
    }

    /**
     * Get the gas stocked
     *
     * @return int
     */
    public int getGasStock() {
        return gasStock;
    }

    /**
     * Set the oil stocked
     *
     * @param oilStock int to set
     */
    public void setOilStock(int oilStock) {
        this.oilStock = oilStock;
    }

    /**
     * Get the oil stocked
     *
     * @return int
     */
    public int getOilStock() {
        return oilStock;
    }

    /**
     * Set the uranium stocked
     *
     * @param uraniumStock int to set
     */
    public void setUraniumStock(int uraniumStock) {
        this.uraniumStock = uraniumStock;
    }

    /**
     * Get the uranium stocked
     *
     * @return int
     */
    public int getUraniumStock() {
        return uraniumStock;
    }

    public void setGlobalHappiness(int globalHappiness){this.globalHappiness = globalHappiness;}

    public int getGlobalhappiness(){return globalHappiness;}
}
