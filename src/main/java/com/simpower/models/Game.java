package com.simpower.models;

import java.nio.file.Path;
import java.time.LocalDateTime;

import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.*;
import com.simpower.models.grid.buildings.mines.CoalMine;
import com.simpower.models.grid.buildings.mines.GasMine;
import com.simpower.models.grid.buildings.mines.OilMine;
import com.simpower.models.grid.buildings.mines.UraniumMine;
import com.simpower.models.grid.buildings.plants.*;
import com.simpower.models.time.Clock;

public class Game implements GridInfos {
    private Clock clock;
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;
    private int money;
    private int electricityStock;
    private int coalStock;
    private int gasStock;
    private int oilStock;
    private int uraniumStock;

    public Game (Grid grid, Clock clock) {
        this.grid = grid;
        this.clock = clock;
        this.createdAt = LocalDateTime.now();
        setMoney(10000);
        setElectricityStock(10000);
        setCoalStock(0);
        setGasStock(0);
        setOilStock(0);
        setUraniumStock(0);
    }

    /**
     * Call operation to be done each day
     */
    public void eachDay() {
        for (Cell[] cellX : this.grid.getCells()) {
            for (Cell cell : cellX) {
                Building building = cell.getCurrentBuilding();
                // todo check if building is not road
                if(building != null) {
                    if (building instanceof CoalMine) {
                        coalStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electricityStock -= building.consumeElectricity();
                    } else if (building instanceof GasMine) {
                        gasStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electricityStock -= building.consumeElectricity();
                    } else if (building instanceof OilMine) {
                        oilStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electricityStock -= building.consumeElectricity();
                    } else if (building instanceof UraniumMine) {
                        uraniumStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electricityStock -= building.consumeElectricity();
                    }else if(building instanceof CoalPlant){
                        coalStock -= building.consumeResource();
                        electricityStock += building.produceElectricity();
                    }else if(building instanceof GasPlant){
                        gasStock -= building.consumeResource();
                        electricityStock += building.produceElectricity();
                    }else if(building instanceof NuclearPlant){
                        uraniumStock -= building.consumeResource();
                        electricityStock += building.produceElectricity();
                    }else if(building instanceof OilPlant) {
                        oilStock -= building.consumeResource();
                        electricityStock += building.produceElectricity();
                    }else if(building instanceof SolarPlant || building instanceof WaterMill || building instanceof WindFarm) {
                        electricityStock += building.produceElectricity();
                    }else if(building instanceof House){
                        money += building.collectMoneyIncomes();
                        electricityStock -= building.consumeElectricity();
                    }else{
                        System.out.println("Error: invalide building on case " + cell.getPos_x() + ":" + cell.getPos_y());
                    }
                }
            }
        }
        // todo: add automatic save
    };

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
}
