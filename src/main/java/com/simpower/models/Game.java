package com.simpower.models;

import java.nio.file.Path;
import java.time.LocalDateTime;

import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
import com.simpower.models.grid.buildings.FossilePlant;
import com.simpower.models.grid.buildings.House;
import com.simpower.models.grid.buildings.Mine;
import com.simpower.models.grid.buildings.ProducerEnergyBuilding;
import com.simpower.models.grid.buildings.mines.CoalMine;
import com.simpower.models.grid.buildings.mines.GasMine;
import com.simpower.models.grid.buildings.mines.OilMine;
import com.simpower.models.grid.buildings.mines.UraniumMine;
import com.simpower.models.grid.buildings.plants.CoalPlant;
import com.simpower.models.grid.buildings.plants.GasPlant;
import com.simpower.models.grid.buildings.plants.NuclearPlant;
import com.simpower.models.grid.buildings.plants.OilPlant;
import com.simpower.models.grid.resources.Oil;
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
                if(cell.getCurrentBuilding() != null) {
                    if(cell.getCurrentBuilding() instanceof ProducerEnergyBuilding) { // Mines
                        if (cell.getCurrentBuilding() instanceof CoalMine) {
                            coalStock += cell.getCurrentBuilding().collectResource();
                        } else if (cell.getCurrentBuilding() instanceof GasMine) {
                            gasStock += cell.getCurrentBuilding().collectResource();
                        } else if (cell.getCurrentBuilding() instanceof OilMine) {
                            oilStock += cell.getCurrentBuilding().collectResource();
                        } else if (cell.getCurrentBuilding() instanceof UraniumMine) {
                            uraniumStock += cell.getCurrentBuilding().collectResource();
                        }
                        cell.getCurrentBuilding().collectMoneyOutcomes();
                        cell.getCurrentBuilding().consumeElectricity();
                    }else if(cell.getCurrentBuilding() instanceof ProducerEnergyBuilding){ // Plants
                        if(cell.getCurrentBuilding() instanceof FossilePlant){ // Fossil plants
                            if(cell.getCurrentBuilding() instanceof CoalPlant){
                                coalStock -= cell.getCurrentBuilding().consumeResource();
                            }else if(cell.getCurrentBuilding() instanceof GasPlant){
                                gasStock -= cell.getCurrentBuilding().consumeResource();
                            }else if(cell.getCurrentBuilding() instanceof NuclearPlant){
                                uraniumStock -= cell.getCurrentBuilding().consumeResource();
                            }else if(cell.getCurrentBuilding() instanceof OilPlant){
                                oilStock -= cell.getCurrentBuilding().consumeResource();
                            }else{
                                System.out.println("Error: invalide building on case " + cell.getPos_x() + ":" + cell.getPos_y());
                            }
                        }
                        electrictyStock += cell.getCurrentBuilding().productElectricity();
                    }else if(cell.getCurrentBuilding() instanceof House){
                        cell.getCurrentBuilding().collectMoneyIncomes();
                        cell.getCurrentBuilding().consumeResource();
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
