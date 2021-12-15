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
import com.simpower.models.grid.buildings.plants.CoalPlant;
import com.simpower.models.grid.buildings.plants.GasPlant;
import com.simpower.models.grid.buildings.plants.NuclearPlant;
import com.simpower.models.grid.buildings.plants.OilPlant;
import com.simpower.models.time.Clock;

public class Game implements GridInfos {
    private Clock clock;
    private LocalDateTime createdAt;
    private Path savePath;
    private Grid grid;
    private int money;
    private int electrictyStock;
    private int coalStock;
    private int gasStock;
    private int oilStock;
    private int uraniumStock;

    public Game (Grid grid, Clock clock) {
        this.grid = grid;
        this.clock = clock;
        this.createdAt = LocalDateTime.now();
        setMoney(1000);
        setElectrictyStock(100);
        setCoalStock(100);
        setGasStock(100);
        setOilStock(100);
        setUraniumStock(100);
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
                        electrictyStock -= building.consumeElectricity();
                    } else if (building instanceof GasMine) {
                        gasStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electrictyStock -= building.consumeElectricity();
                    } else if (building instanceof OilMine) {
                        oilStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electrictyStock -= building.consumeElectricity();
                    } else if (building instanceof UraniumMine) {
                        uraniumStock += building.collectResource();
                        money -= building.collectMoneyOutcomes();
                        electrictyStock -= building.consumeElectricity();
                    }else if(building instanceof CoalPlant){
                        coalStock -= building.consumeResource();
                        electrictyStock += building.produceElectricity();
                    }else if(building instanceof GasPlant){
                        gasStock -= building.consumeResource();
                        electrictyStock += building.produceElectricity();
                    }else if(building instanceof NuclearPlant){
                        uraniumStock -= building.consumeResource();
                        electrictyStock += building.produceElectricity();
                    }else if(building instanceof OilPlant){
                        oilStock -= building.consumeResource();
                        electrictyStock += building.produceElectricity();
                    }else if(building instanceof House){
                        money += building.collectMoneyIncomes();
                        electrictyStock -= building.consumeElectricity();
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

    public void setElectrictyStock(int electrictyStock) {
        this.electrictyStock = electrictyStock;
    }

    public int getElectrictyStock() {
        return electrictyStock;
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
