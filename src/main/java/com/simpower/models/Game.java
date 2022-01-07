package com.simpower.models;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.*;

import static java.lang.Math.abs;

public class Game implements GridInfos{
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;
    private int money;
    private int globalHappiness;
    private int electricityProduced;
    private int coalStock;
    private int gasStock;
    private int oilStock;
    private int uraniumStock;
    private boolean gameOver;
    private Map <resourceStock, Function<Integer, Integer>> resourceStockToStockMap = new HashMap<>();

    public Game (Grid grid) {
        this.grid = grid;
        this.createdAt = LocalDateTime.now();
        setGameOver(false);
        setMoney(100000);
        setGlobalHappiness(100);
        setElectricityProduced(0);
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
        electricityProduced = 0;
        ArrayList<Building> houses = new ArrayList<Building>();
        ArrayList<Building> mines = new ArrayList<Building>();
        int tmpHappiness = 0;
        int houseCount = 0;
        for (Cell[] cellX : this.grid.getCells()) {
            for (Cell cell : cellX) {

                // Handle pollution
                if(cell.isPolluted()) {
                    if(cell.getPollutionAge() > POLLUTION_PERSISTENCE_DAY) {
                        // Pollution disappear after a certain number of day without being re-polluted
                        cell.setPolluted(false);
                        // If there's a building, we tell the building it's not polluted anymore
                        if(!cell.isBuildingEmpty())
                            cell.getCurrentBuilding().setCellPolluted(false);
                    } else
                        // Increment pollution age
                        cell.setPollutionAge(cell.getPollutionAge() + 1);
                }

                // Handle building
                Building building = cell.getCurrentBuilding();
                if (building == null || building.isRoad())
                    continue; // continue loop if the building is a road or null

                // Store mines and building in an arraylist
                if(building.isMine())
                    mines.add(building);

                if (building.isHouse()) {
                    houses.add(building);
                    houseCount++;
                } else
                    // Set the building as active if there is enough money for the servicing cost
                    building.setActive(abs(building.changeMoneyAmount()) < this.money);

                if (building.isFossil() && building.isActive() && building.isEnergyProducer()) // fossil plant
                        // set the fossil plant as active if there is enough resources
                        building.setActive(abs(building.resourceStockChange()) < resourceStockToStockMap.get(building.getResourceStockEnum()).apply(0));

                // Handle active plants
                if (building.isActive()) {
                    if (building.isEnergyProducer()) { // Plants
                        if(building.isFossil()) { // Fossil plants
                            resourceStockToStockMap.get(building.getResourceStockEnum()).apply(building.resourceStockChange());
                            // Generate pollution around plants
                            this.grid.generatePollutionAroundCell(cell);
                        }
                        // Add energy production to electricityProduced and remove the servicing cost from the global money
                        this.electricityProduced += building.electricityStockChange();
                        this.money += building.changeMoneyAmount();
                    }
                }
            }
        }

        // Check if the mines is active
        for (Building mine:mines) {
            mine.setActive(abs(mine.electricityStockChange()) <= this.electricityProduced && abs(mine.changeMoneyAmount()) <= this.money);
            if(mine.isActive()){
                // Collect resource, consume electricity and pay servicing cost
                resourceStockToStockMap.get(mine.getResourceStockEnum()).apply(mine.resourceStockChange());
                this.electricityProduced += mine.electricityStockChange();
                this.money += mine.changeMoneyAmount();
            }
        }

        if(houseCount > 0) {
            // Compute the electricity we can provide to each house
            float electricityProvided = electricityProduced / houseCount;
            for (Building house : houses) {
                // Calculate happiness and add money incomes to the global money
                tmpHappiness += house.updateHappiness(electricityProvided);
                this.money += house.changeMoneyAmount();
            }
        }

        if(houseCount >0)
            globalHappiness = tmpHappiness / houseCount;

        if(globalHappiness <= 0) {
            this.gameOver = true;
        }
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
     * @param electricityProduced int to set
     */
    public void setElectricityProduced(int electricityProduced) {
        this.electricityProduced = electricityProduced;
    }

    /**
     * Get the electricity stocked
     *
     * @return int
     */
    public int getElectricityProduced() {
        return electricityProduced;
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

    /**
     * Set the global hapiness;
     * @param globalHappiness
     */
    public void setGlobalHappiness(int globalHappiness){this.globalHappiness = globalHappiness;}

    /**
     * Get the global hapiness
     * @return int
     */
    public int getGlobalhappiness() { return this.globalHappiness; }

    /**
     * Set if the game is over
     * @param gameOver
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Tell if the game is over
     * @return
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
